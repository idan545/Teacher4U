package com.example.betaidan;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FBref {
    public static FirebaseAuth refAuth=FirebaseAuth.getInstance();
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refstudent=FBDB.getReference("Students");
    public static DatabaseReference refTeacher=FBDB.getReference("Teachers");
    public static DatabaseReference refLocations=FBDB.getReference("OrderReq");
    public static DatabaseReference refLessonOffer=FBDB.getReference("LessonOffer");
    public static DatabaseReference refHistory=FBDB.getReference("Order History");

    public static FirebaseStorage FBST = FirebaseStorage.getInstance();
    public static StorageReference refStor=FBST.getReference();
    public static StorageReference refImages=refStor.child("Images");

}