package com.example.pz2326;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView rvCartItems;
    private TextView tvTotalPrice;
    private Button btnCheckout;

    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        rvCartItems = view.findViewById(R.id.rv_cart_items);
        tvTotalPrice = view.findViewById(R.id.tv_total_price);
        btnCheckout = view.findViewById(R.id.btn_checkout);

        loadCartItems();

        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter = new CartAdapter(cartItems, totalPrice -> {
            tvTotalPrice.setText(totalPrice + " ₽");
        });
        rvCartItems.setAdapter(cartAdapter);

        btnCheckout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Переход к оформлению", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void loadCartItems() {
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Клинический анализ крови с лейкоцитарной формулой", "690 ₽", 1));
        cartItems.add(new CartItem("ПЦР-тест на определение РНК коронавируса стандартный", "1800 ₽", 1));
    }

    public static class CartItem {
        private String name;
        private String price;
        private int quantity;

        public CartItem(String name, String price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String getName() { return name; }
        public String getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
        private List<CartItem> items;
        private OnTotalPriceChangeListener listener;

        public CartAdapter(List<CartItem> items, OnTotalPriceChangeListener listener) {
            this.items = items;
            this.listener = listener;
        }

        @Override
        public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
            return new CartViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CartViewHolder holder, int position) {
            CartItem item = items.get(position);
            holder.tvName.setText(item.getName());
            holder.tvPrice.setText(item.getPrice());
            holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

            holder.btnRemove.setOnClickListener(v -> {
                items.remove(position);
                notifyItemRemoved(position);
                updateTotalPrice();
            });

            holder.btnDecrease.setOnClickListener(v -> {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
                    updateTotalPrice();
                }
            });

            holder.btnIncrease.setOnClickListener(v -> {
                item.setQuantity(item.getQuantity() + 1);
                holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
                updateTotalPrice();
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        private void updateTotalPrice() {
            int total = 0;
            for (CartItem item : items) {
                String priceStr = item.getPrice().replace(" ₽", "");
                total += Integer.parseInt(priceStr) * item.getQuantity();
            }
            if (listener != null) {
                listener.onTotalPriceChanged(total);
            }
        }

        class CartViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvPrice, tvQuantity;
            ImageButton btnRemove;
            TextView btnDecrease, btnIncrease;

            CartViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_analysis_name);
                tvPrice = itemView.findViewById(R.id.tv_price);
                tvQuantity = itemView.findViewById(R.id.tv_quantity);
                btnRemove = itemView.findViewById(R.id.btn_remove);
                btnDecrease = itemView.findViewById(R.id.btn_decrease);
                btnIncrease = itemView.findViewById(R.id.btn_increase);
            }
        }
    }

    public interface OnTotalPriceChangeListener {
        void onTotalPriceChanged(int totalPrice);
    }
}