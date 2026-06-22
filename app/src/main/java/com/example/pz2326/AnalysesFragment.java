package com.example.pz2326;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class AnalysesFragment extends Fragment {

    private EditText etSearch;
    private RecyclerView rvPromotions, rvAnalyses;
    private RadioGroup rgCategories;

    private PromotionAdapter promotionAdapter;
    private AnalysisAdapter analysisAdapter;
    private List<Promotion> promotionList;
    private List<Analysis> analysisList;
    private List<Analysis> fullAnalysisList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analyses, container, false);

        etSearch = view.findViewById(R.id.et_search);
        rvPromotions = view.findViewById(R.id.rv_promotions);
        rvAnalyses = view.findViewById(R.id.rv_analyses);
        rgCategories = view.findViewById(R.id.rg_categories);

        loadPromotions();
        loadAnalyses();

        rvPromotions.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        promotionAdapter = new PromotionAdapter(promotionList);
        rvPromotions.setAdapter(promotionAdapter);

        rvAnalyses.setLayoutManager(new LinearLayoutManager(getContext()));
        analysisAdapter = new AnalysisAdapter(analysisList);
        rvAnalyses.setAdapter(analysisAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterAnalyses(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        rgCategories.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_popular) {
                filterByCategory("popular");
            } else if (checkedId == R.id.rb_covid) {
                filterByCategory("covid");
            } else if (checkedId == R.id.rb_complex) {
                filterByCategory("complex");
            }
        });

        return view;
    }

    private void loadPromotions() {
        promotionList = new ArrayList<>();
        promotionList.add(new Promotion("Чек-ап для мужчин", "9 исследований", "8000 ₽"));
        promotionList.add(new Promotion("Подготовка к вакцинации", "5 исследований", "4000 ₽"));
    }

    private void loadAnalyses() {
        fullAnalysisList = new ArrayList<>();
        fullAnalysisList.add(new Analysis("ПЦР-тест на определение РНК коронавируса стандартный", "2 дня", "1800 ₽", "popular"));
        fullAnalysisList.add(new Analysis("Клинический анализ крови с лейкоцитарной формулировкой", "1 день", "690 ₽", "popular"));
        fullAnalysisList.add(new Analysis("Биохимический анализ крови, базовый", "1 день", "2440 ₽", "popular"));
        fullAnalysisList.add(new Analysis("ПЦР-тест на определение РНК коронавируса стандартный", "2 дня", "1800 ₽", "covid"));
        fullAnalysisList.add(new Analysis("Антитела к коронавирусу IgG", "1 день", "1500 ₽", "covid"));
        fullAnalysisList.add(new Analysis("СОЭ (венозная кровь)", "1 день", "500 ₽", "complex"));
        fullAnalysisList.add(new Analysis("Общий анализ мочи", "1 день", "450 ₽", "complex"));

        analysisList = new ArrayList<>(fullAnalysisList);
    }

    private void filterAnalyses(String query) {
        List<Analysis> filteredList = new ArrayList<>();
        for (Analysis analysis : fullAnalysisList) {
            if (analysis.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(analysis);
            }
        }
        analysisList.clear();
        analysisList.addAll(filteredList);
        analysisAdapter.notifyDataSetChanged();
    }

    private void filterByCategory(String category) {
        List<Analysis> filteredList = new ArrayList<>();
        for (Analysis analysis : fullAnalysisList) {
            if (analysis.getCategory().equals(category)) {
                filteredList.add(analysis);
            }
        }
        analysisList.clear();
        analysisList.addAll(filteredList);
        analysisAdapter.notifyDataSetChanged();
    }

    public static class Promotion {
        private String title, tests, price;
        public Promotion(String title, String tests, String price) {
            this.title = title; this.tests = tests; this.price = price;
        }
        public String getTitle() { return title; }
        public String getTests() { return tests; }
        public String getPrice() { return price; }
    }

    public static class Analysis {
        private String name, days, price, category;
        public Analysis(String name, String days, String price, String category) {
            this.name = name; this.days = days; this.price = price; this.category = category;
        }
        public String getName() { return name; }
        public String getDays() { return days; }
        public String getPrice() { return price; }
        public String getCategory() { return category; }
    }

    public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> {
        private List<Promotion> promotions;
        public PromotionAdapter(List<Promotion> promotions) { this.promotions = promotions; }

        @Override
        public PromotionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promotion, parent, false);
            return new PromotionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PromotionViewHolder holder, int position) {
            Promotion p = promotions.get(position);
            holder.tvTitle.setText(p.getTitle());
            holder.tvTests.setText(p.getTests());
            holder.tvPrice.setText(p.getPrice());
        }

        @Override
        public int getItemCount() { return promotions.size(); }

        class PromotionViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvTests, tvPrice;
            PromotionViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_promo_title);
                tvTests = itemView.findViewById(R.id.tv_promo_tests);
                tvPrice = itemView.findViewById(R.id.tv_promo_price);
            }
        }
    }

    public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.AnalysisViewHolder> {
        private List<Analysis> analyses;
        public AnalysisAdapter(List<Analysis> analyses) { this.analyses = analyses; }

        @Override
        public AnalysisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_analysis, parent, false);
            return new AnalysisViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AnalysisViewHolder holder, int position) {
            Analysis a = analyses.get(position);
            holder.tvName.setText(a.getName());
            holder.tvDays.setText(a.getDays());
            holder.tvPrice.setText(a.getPrice());
            holder.btnAdd.setOnClickListener(v ->
                    Toast.makeText(getContext(), "Добавлено: " + a.getName(), Toast.LENGTH_SHORT).show());
        }

        @Override
        public int getItemCount() { return analyses.size(); }

        class AnalysisViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvDays, tvPrice;
            Button btnAdd;
            AnalysisViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_analysis_name);
                tvDays = itemView.findViewById(R.id.tv_analysis_days);
                tvPrice = itemView.findViewById(R.id.tv_analysis_price);
                btnAdd = itemView.findViewById(R.id.btn_add);
            }
        }
    }
}