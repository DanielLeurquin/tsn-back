package com.isep.tsn.helper;

import lombok.Data;

import java.util.*;

public class Helper {
    public static QuickSortList recursiveQuickSort(QuickSortList<?> quickSortList){
        if(quickSortList.weights.size() <= 1){
            return quickSortList;
        }
        var pivot = quickSortList.weights.get(quickSortList.weights.size()/2 +
                quickSortList.weights.size()%2);

        var middle = quickSortList.equalWeight(pivot);
        var left =  quickSortList.lowerThanWeight(pivot);
        var right = quickSortList.greaterThanWeight(pivot);

        return concatenateQuickSortLists(new QuickSortList[]{
                recursiveQuickSort(left),
                middle,
                recursiveQuickSort(right)
        });
    }


    private static QuickSortList concatenateQuickSortLists(QuickSortList[] array){
        var objects = new ArrayList<Object>();
        var weights = new ArrayList<Integer>();

        for(int i = 0; i < array.length; i++){
            weights.addAll(array[i].weights);
            objects.addAll(array[i].objects);
        }

        return new QuickSortList(objects,weights);
    }


}




