package com.isep.tsn.helper;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuickSortList<T> {
    List<T> objects;
    List<Double> weights;

    public QuickSortList(List<T> objects, List<Double> weights){
        this.weights = weights;
        this.objects = objects;
    }

    public QuickSortList<T> greaterThanWeight(Double weight){
        var output = new QuickSortList<T>(new ArrayList<>(), new ArrayList<>());
        for(int i = 0; i < this.weights.size(); i++){
            if(this.weights.get(i) > weight){
                output.weights.add(this.weights.get(i));
                output.objects.add(this.objects.get(i));
            }
        }
        return output;
    }

    public QuickSortList<T> lowerThanWeight(Double weight){
        var output = new QuickSortList<T>(new ArrayList<>(), new ArrayList<>());
        for(int i = 0; i < this.weights.size(); i++){
            if(this.weights.get(i) < weight){
                output.weights.add(this.weights.get(i));
                output.objects.add(this.objects.get(i));
            }
        }
        return output;
    }

    public QuickSortList<T> equalWeight(Double weight){
        var output = new QuickSortList<T>(new ArrayList<>(), new ArrayList<>());
        for(int i = 0; i < this.weights.size(); i++){
            if(this.weights.get(i) == weight){
                output.weights.add(this.weights.get(i));
                output.objects.add(this.objects.get(i));
            }
        }
        return output;
    }


}