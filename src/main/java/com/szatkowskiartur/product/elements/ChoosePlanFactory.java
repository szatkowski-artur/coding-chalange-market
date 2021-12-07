package com.szatkowskiartur.product.elements;

import com.szatkowskiartur.product.Product;
import com.szatkowskiartur.product.ProductType;

import java.lang.reflect.InvocationTargetException;

public class ChoosePlanFactory {

    public static Plan choosePlan(ProductType productType) {

        try {
            String type = productType.name();

            switch (type) {
                case "COIN":
                    CoinPlan.class.getDeclaredConstructor().newInstance();
                case "STOCK":
                    StockPlan.class.getDeclaredConstructor().newInstance();
                default:
                    return null;
            }
        } catch (NoSuchMethodException e) {

        }catch (InvocationTargetException e) {

        }catch (InstantiationException e) {

        }catch (IllegalAccessException e) {

        }

        return null;
    }

}
