package info.vault.com.myvault.Listeners;


import java.util.ArrayList;

import info.vault.com.myvault.Pojo.RecordsPojo;

public interface itemClickListener {
     void addToCart(ArrayList<RecordsPojo> item, int position);
}
