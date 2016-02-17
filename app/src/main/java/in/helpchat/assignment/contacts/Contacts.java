package in.helpchat.assignment.contacts;

/**
 * Created by admin on 15/02/16.
 */
public class Contacts    {
    int _id;
    String _name;
    String _phone_number;
    String _image_path;

    public Contacts(){

    }

    public Contacts( int id,String name, String phone_number,String image_path){
        this._id=id;
        this._name = name;
        this._phone_number = phone_number;
        this._image_path=image_path;
    }
    // constructor
    public Contacts( String name, String phone_number,String image_path){
        this._name = name;
        this._phone_number = phone_number;
        this._image_path=image_path;
    }

    // constructor
    public Contacts(String name, String phone_number){
        this._name = name;
        this._phone_number = phone_number;
    }
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    public void setImagePath(String image_path){
        this._image_path=image_path;
    }

    public String getImagePath(){
        return this._image_path;
    }

}
