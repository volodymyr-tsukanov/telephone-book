package com.IO.telephoneBook;

import java.util.ArrayList;


public class Contact {
		int id;
		PhoneNumber phoneNumber;
		String name, surname, job, email, address, notes;
		
		
		public Contact(final Contact _contact){
				setPhoneNumber(_contact.getPhoneNumber());
				setName(_contact.getName());
				setSurname(_contact.getSurname());
				setJob(_contact.getJob());
				setEmail(_contact.getEmail());
				setAddress(_contact.getAddress());
				setNotes(_contact.getNotes());
				
				updateId();
		}
		public Contact(String _data){
				fromString(_data);
				
				updateId();
		}
		public Contact(String _phoneNumber, String _name, String _surname, String _job, String _email, String _address, String _notes){
				setPhoneNumber(_phoneNumber);
				setName(_name);
				setSurname(_surname);
				setJob(_job);
				setEmail(_email);
				setAddress(_address);
				setNotes(_notes);
				
				updateId();
		}
		
		
		void updateId(){
				id = (int)(getName().charAt(0)) + (int)(getSurname().charAt(0))
				+ (int)(getJob().charAt(0)) + (int)(getEmail().charAt(0))
				+ (int)(getAddress().charAt(0)) + (int)(getNotes().charAt(0));
		}
		
		
		public int getId(){
				updateId();
				
				return id;
		}
		
		public final PhoneNumber getPhoneNumber(){
				return phoneNumber;
		}
		
		public String getName(){
				//return (name.equals(Character.toString(Serializer.nullC))) ? "" : name;
				return name;
		}
		public String getSurname(){
				return surname;
		}
		public String getFullName(){
				return getName() + " " + getSurname();
		}
		
		public String getJob(){
				return job;
		}
		
		public String getEmail(){
				return email;
		}
		
		public String getAddress(){
				return address;
		}
		
		public String getNotes(){
				return notes;
		}
		
		
		public void setPhoneNumber(PhoneNumber _phoneNumber){
				phoneNumber = new PhoneNumber(_phoneNumber);
		}
		public void setPhoneNumber(String _phoneNumber){
				phoneNumber = new PhoneNumber((_phoneNumber.isEmpty()) ? Character.toString(Serializer.nullC) : _phoneNumber);
		}
		
		public void setName(String _name){
				name = (_name.isEmpty()) ? Character.toString(Serializer.nullC) : _name;
		}
		
		public void setSurname(String _surname){
				surname = (_surname == null || _surname.isEmpty()) ? Character.toString(Serializer.nullC) : _surname;
		}
		
		public void setJob(String _job){
				job = (_job.isEmpty()) ? Character.toString(Serializer.nullC) : _job;
		}
		
		public void setEmail(String _email){
				email = (_email.isEmpty()) ? Character.toString(Serializer.nullC) : _email;
		}
		
		public void setAddress(String _address){
				address = (_address.isEmpty()) ? Character.toString(Serializer.nullC) : _address;
		}
		
		public void setNotes(String _notes){
				notes = (_notes.isEmpty()) ? Character.toString(Serializer.nullC) : _notes;
		}
		
		
		public void fromString(String input){
				String[] ss = new String[8];
				String[] ss2 = input.split(Character.toString(Serializer.unitSeparator));
				if(ss.length == ss2.length) ss = ss2;
				else{
						for(int i = 0; i < ss2.length; i++) ss[i] = ss2[i];
						for(int i = ss2.length-1; i < ss.length; i++) ss[i] = "";
				}
				
				setPhoneNumber(ss[1]);
				setName(ss[2]);
				setSurname(ss[3]);
				setJob(ss[4]);
				setEmail(ss[5]);
				setAddress(ss[6]);
				setNotes(ss[7]);
		}
		
		public String toString(String separator){
				String output = String.valueOf(getId()) + separator + phoneNumber.getFullNumber() + separator
				+ name + separator + surname + separator
				+ job + separator + email + separator + address + separator + notes;
				
				return output;
		}
		public String toString(){
				return toString(Character.toString(Serializer.unitSeparator));
		}
		public String toString(boolean forView, String separator){
				String output = "Job: " + job + separator + "Email: " + email + separator
				+ "Address: " + address + separator + "Notes: " + notes;
				
				if(!forView){
						output = "Phone number: " + phoneNumber.getFullNumber() + separator
						+ "Name: " + name + separator + "Surname: " + surname + separator + output;
				}
				
				return output;
		}
		
		
		public class PhoneNumber {
				String number;
				int isBad = 0;
				
				
				public PhoneNumber(PhoneNumber _phoneNumber){
						number = _phoneNumber.getFullNumber();
						
						checkNumber();
				}
				public PhoneNumber(String countryCode, String mainPart){
						number = countryCode + mainPart;
						
						checkNumber();
				}
				public PhoneNumber(String _number){
						number = _number;
						
						checkNumber();
				}
				
				
				public boolean checkNumber(){
						if(isBad == 0){
								if(number.length() < 9 || number.length() > 12) isBad = -5; //general number length exception
						}
						
						return isBad > 0;
				}
				
				
				public String getFullNumber(){
						return number;
				}
				
				public String getCountryCode(){
						if(checkNumber()){
								String cc = number.substring(0, number.length()-10);
								
								return cc;
						}
						
						return "";
				}
				
				public String getMainPart(){
						if(checkNumber()){
								String mp = number.substring(number.length()-10, number.length()-1);
								
								return mp;
						}
						
						return number;
				}
		}
		
		
		public static class Serializer {
				public static final char nullC = (char)0, groupSeparator = (char)29, recordSeparator = (char)30, unitSeparator = (char)31;
				
				
				public static ArrayList<Contact> fromString(String input){
						ArrayList<Contact> contacts = new ArrayList<Contact>();
						
						if(input.length() == 0) return contacts;
						
						String[] ss = input.split(Character.toString(recordSeparator));
						for(int i = 0; i < ss.length; i++){
								Contact contact = new Contact(ss[i]);
								
								contacts.add(contact);
						}
						
						return contacts;
				}
				
				public static String toString(final ArrayList<Contact> contacts){
						if(contacts.size() == 0) return "";
						
						String output = contacts.get(0).toString();
						for(int i = 1; i < contacts.size(); i++){
								output += recordSeparator + contacts.get(i).toString();
						}
						
						return output;
				}
		}
}
