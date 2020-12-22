package com.itechpandas.itechpanda.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.itechpandas.itechpanda.model.AddNotices;
import com.itechpandas.itechpanda.model.userInfo;
import com.itechpandas.itechpanda.model.userLogData;

@Service
public class FirebaseInitializer {

	@PostConstruct
	private void initDB() throws IOException {
		InputStream serviceAccount = this.getClass().getClassLoader()
				.getResourceAsStream("./firesdk.json");

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://desifarmings-default-rtdb.firebaseio.com").build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
	}

	public Firestore getFirebase() {
		return FirestoreClient.getFirestore();
	}
	
	public boolean Login(String email, String password) throws FirebaseAuthException, InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference docRef = db.collection("emplyee").document(email);
		// asynchronously retrieve the document
		ApiFuture<DocumentSnapshot> future = docRef.get();
		// block on response
		DocumentSnapshot document = future.get();
		userLogData city = null;
		
		if (document.exists()) {
		  // convert document to POJO
		  city = document.toObject(userLogData.class);
		 
		  String uname = city.email;
		  if(uname.equals(email) && password.equals(city.password)) {
		  
			 return true;
		  }
		} else {
		  System.out.println("No such document!");
		}
		return false;
	}
	
	public AddNotices getNotice(String title) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference docRef = db.collection("notice").document(title);
		// asynchronously retrieve the document
		ApiFuture<DocumentSnapshot> future = docRef.get();
		// block on response
		DocumentSnapshot document = future.get();
		AddNotices city = null;
		if (document.exists()) {
			// convert document to POJO
			city = document.toObject(AddNotices.class);
			
			System.out.println(city.message+city.title);
			
		}else {
			System.out.println("No such document!");
		}
		return city;
				
	}
	
}
