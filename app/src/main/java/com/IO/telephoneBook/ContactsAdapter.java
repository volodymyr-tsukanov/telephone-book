package com.IO.telephoneBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Locale;
import java.util.Collections;
import java.text.Collator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
		ArrayList<Contact> data;
		HashMap<String, Integer> firstLetters;
		
		LayoutInflater inflater;
		AssetManager assetManager;
		ItemClickListener itemClickListener;
		
		
		public ContactsAdapter(final Context _context, ArrayList<Contact> _data){
				inflater = LayoutInflater.from(_context);
				assetManager = _context.getAssets();
				firstLetters = new HashMap<>();
				
				data = _data;
		}
		
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
				View view = inflater.inflate(R.layout.contact, null);
				RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
				view.setLayoutParams(_lp);
				return new ViewHolder(view);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position){
				View _view = _holder.itemView;
				
				Contact contact = data.get(_position);
				
				final LinearLayout body = (LinearLayout) _view.findViewById(R.id.body);
				final LinearLayout content_lin = (LinearLayout) _view.findViewById(R.id.content_lin);
				final TextView name_text = (TextView) _view.findViewById(R.id.name_text);
				final TextView number_text = (TextView) _view.findViewById(R.id.number_text);
				
				try{
						//Style
						content_lin.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)5, (int)1, 0xFFFFD970, 0xFFFFD966));
						name_text.setTypeface(Typeface.createFromAsset(assetManager, "fonts/iconic_font.ttf"), 0);
						number_text.setTypeface(Typeface.createFromAsset(assetManager, "fonts/iconic_font.ttf"), 0);
						
						//Visualization
						name_text.setText(contact.getFullName());
						number_text.setText(contact.getPhoneNumber().getFullNumber());
				} catch(Exception e) {
						
				}
		}
		
		@Override
		public int getItemCount(){
				return data.size();
		}
		
		
		void updateFirstLetters(final Sort.SortParam sortParam){
				firstLetters.clear();
				
				String prevL = "";
				
				for(int i = 0; i < data.size(); i++){
						String l = "";
						switch(sortParam){
								case Name:
								l = getItem(i).getName();
								break;
								
								case Surname:
								l = getItem(i).getSurname();
								break;
								
								case Job:
								l = getItem(i).getJob();
								break;
								
								case Email:
								l = getItem(i).getEmail();
								break;
								
								case Address:
								l = getItem(i).getAddress();
								break;
								
								case Notes:
								l = getItem(i).getNotes();
								break;
								
								default:
								l = getItem(i).getName();
								break;
						}
						l = l.substring(0, 1);
						
						if(l != prevL){
								prevL = l;
								
								firstLetters.put(l, i);
						}
				}
		}
		
		
		public final ArrayList<Contact> getData(){
				return data;
		}
		
		public final Contact getItem(int _position){
				return data.get(_position);
		}
		
		public String[] getFirstLetters(){
				Object[] arr = firstLetters.keySet().toArray();
				String[] output = new String[arr.length];
				for(int i = 0; i < arr.length; i++) output[i] = (String)arr[i];
				
				return output;
		}
		
		public int getFirstLetterPosition(String letter){
				if(firstLetters.containsKey(letter)) return firstLetters.get(letter);
				else return -1;
		}
		
		
		public void setData(ArrayList<Contact> _data){
				data = _data;
				
				notifyDataSetChanged();
		}
		
		public void addItem(Contact _item){
				data.add(_item);
				
				notifyItemInserted(getItemCount()-1);
		}
		
		public void setItem(int _position, Contact _item){
				if(_position < 0 && _position > data.size()) return;
				data.set(_position, _item);
				
				notifyItemChanged(_position);
		}
		
		public void removeItem(int _position){
				data.remove(_position);
				
				notifyItemRemoved(_position);
		}
		
		public void setClickListener(ItemClickListener _itemClickListener){
				itemClickListener = _itemClickListener;
		}
		
		
		public void sort(final Sort.SortParam sortParam){
				Collections.sort(data, Sort.sortBy(sortParam));
				
				notifyDataSetChanged();
				
				updateFirstLetters(sortParam);
		}
		public void sort(){
				sort(Sort.SortParam.Default);
		}
		
		
		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
				public ViewHolder(View itemView){
						super(itemView);
						
						itemView.setOnClickListener(this);
				}
				
				@Override
				public void onClick(View view){
						if (itemClickListener != null) itemClickListener.onItemClicked(view, getAdapterPosition());
				}
		}
		
		
		public static class Sort {
				public enum SortParam { Default, Name, Surname, Job, Email, Address, Notes; }
				
				
				public static Comparator<Contact> sortBy(final SortParam sortParam){
						Comparator<Contact> cmp;
						
						switch(sortParam){
								case Name:
								cmp = new Comparator<Contact>(){
										@Override
										public int compare(final Contact c1, final Contact c2){
												int c;
												
												c = Sort.compare(c1.getName(), c2.getName());
												if(c == 0) c = Sort.compare(c1.getSurname(), c2.getSurname());
												if(c == 0) c = Sort.compare(c1.getJob(), c2.getJob());
												if(c == 0) c = Sort.compare(c1.getEmail(), c2.getEmail());
												if(c == 0) c = Sort.compare(c1.getAddress(), c2.getAddress());
												if(c == 0) c = Sort.compare(c1.getNotes(), c2.getNotes());
												if(c == 0) c = Integer.compare(c1.getId(), c2.getId());
												
												return c;
										}
								};
								break;
								
								case Surname:
								cmp = new Comparator<Contact>(){
										@Override
										public int compare(final Contact c1, final Contact c2){
												int c;
												
												c = Sort.compare(c1.getSurname(), c2.getSurname());
												if(c == 0) c = Sort.compare(c1.getName(), c2.getName());
												if(c == 0) c = Sort.compare(c1.getJob(), c2.getJob());
												if(c == 0) c = Sort.compare(c1.getEmail(), c2.getEmail());
												if(c == 0) c = Sort.compare(c1.getAddress(), c2.getAddress());
												if(c == 0) c = Sort.compare(c1.getNotes(), c2.getNotes());
												if(c == 0) c = Integer.compare(c1.getId(), c2.getId());
												
												return c;
										}
								};
								break;
								
								case Job:
								cmp = new Comparator<Contact>(){
										@Override
										public int compare(final Contact c1, final Contact c2){
												int c;
												
												c = Sort.compare(c1.getJob(), c2.getJob());
												if(c == 0) c = Sort.compare(c1.getName(), c2.getName());
												if(c == 0) c = Sort.compare(c1.getSurname(), c2.getSurname());
												if(c == 0) c = Sort.compare(c1.getEmail(), c2.getEmail());
												if(c == 0) c = Sort.compare(c1.getAddress(), c2.getAddress());
												if(c == 0) c = Sort.compare(c1.getNotes(), c2.getNotes());
												if(c == 0) c = Integer.compare(c1.getId(), c2.getId());
												
												return c;
										}
								};
								break;
								
								case Email:
								cmp = new Comparator<Contact>(){
										@Override
										public int compare(final Contact c1, final Contact c2){
										    int c;
												
												c = Sort.compare(c1.getEmail(), c2.getEmail());
												if(c == 0) c = Sort.compare(c1.getName(), c2.getName());
												if(c == 0) c = Sort.compare(c1.getSurname(), c2.getSurname());
												if(c == 0) c = Sort.compare(c1.getJob(), c2.getJob());
												if(c == 0) c = Sort.compare(c1.getAddress(), c2.getAddress());
												if(c == 0) c = Sort.compare(c1.getNotes(), c2.getNotes());
												if(c == 0) c = Integer.compare(c1.getId(), c2.getId());
												
												return c;
										}
								};
								break;
								
								case Address:
								cmp = new Comparator<Contact>(){
										@Override
										public int compare(final Contact c1, final Contact c2){
												int c;
												
												c = Sort.compare(c1.getAddress(), c2.getAddress());
												if(c == 0) c = Sort.compare(c1.getName(), c2.getName());
												if(c == 0) c = Sort.compare(c1.getSurname(), c2.getSurname());
												if(c == 0) c = Sort.compare(c1.getJob(), c2.getJob());
												if(c == 0) c = Sort.compare(c1.getEmail(), c2.getEmail());
												if(c == 0) c = Sort.compare(c1.getNotes(), c2.getNotes());
												if(c == 0) c = Integer.compare(c1.getId(), c2.getId());
												
												return c;
										}
								};
								break;
								
								case Notes:
								cmp = new Comparator<Contact>(){
										@Override
										public int compare(final Contact c1, final Contact c2){
												int c;
												
												c = Sort.compare(c1.getNotes(), c2.getNotes());
												if(c == 0) c = Sort.compare(c1.getName(), c2.getName());
												if(c == 0) c = Sort.compare(c1.getSurname(), c2.getSurname());
												if(c == 0) c = Sort.compare(c1.getJob(), c2.getJob());
												if(c == 0) c = Sort.compare(c1.getEmail(), c2.getEmail());
												if(c == 0) c = Sort.compare(c1.getAddress(), c2.getAddress());
												if(c == 0) c = Integer.compare(c1.getId(), c2.getId());
												
												return c;
										}
								};
								break;
								
								default:
								cmp = new Comparator<Contact>(){
										@Override
										public int compare(final Contact c1, final Contact c2){
												int c;
												
												c = Sort.compare(c1.getName(), c2.getName());
												if(c == 0) c = Sort.compare(c1.getSurname(), c2.getSurname());
												if(c == 0) c = Sort.compare(c1.getJob(), c2.getJob());
												if(c == 0) c = Sort.compare(c1.getEmail(), c2.getEmail());
												if(c == 0) c = Sort.compare(c1.getAddress(), c2.getAddress());
												if(c == 0) c = Sort.compare(c1.getNotes(), c2.getNotes());
												if(c == 0) c = Integer.compare(c1.getId(), c2.getId());
												
												return c;
										}
								};
								break;
						}
						
						return cmp;
				}
				
				
				public static int compare(String str1, String str2){
				   Locale locale = new Locale("pl", "PL");
						Collator collator = Collator.getInstance(locale);
						
						return collator.compare(str1, str2);
				}
		}
		
		
		public interface ItemClickListener {
				public void onItemClicked(final View view, final int position);
		}
}
