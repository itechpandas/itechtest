package com.itechpandas.itechpanda.controllers;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itechpandas.itechpanda.model.AddNotices;
import com.itechpandas.itechpanda.model.userInfo;
import com.itechpandas.itechpanda.model.userLogData;
import com.itechpandas.itechpanda.service.FirebaseInitializer;

@RequestMapping("/auth")
@RestController
public class authController {

	@Autowired
	FirebaseInitializer db;

	@RequestMapping("/login")
	public ModelAndView login(userLogData uld, HttpServletRequest request)
			throws FirebaseAuthException, InterruptedException, ExecutionException {
		ModelAndView mv = new ModelAndView();

		mv.addObject("log", uld);
		mv.addObject("name", "dksingh");
		boolean b = db.Login(uld.email, uld.password);
		AddNotices and = db.getNotice("hii");
		if (b) {
			

			mv.setViewName("dashboard");
			
			Firestore db = FirestoreClient.getFirestore();
			DocumentReference docRef = db.collection("emplyee").document(uld.email);
			// asynchronously retrieve the document
			ApiFuture<DocumentSnapshot> future = docRef.get();
			// block on response
			DocumentSnapshot document = future.get();
			userInfo city = null;
			if (document.exists()) {
				// convert document to POJO
				city = document.toObject(userInfo.class);
				
				System.out.println(city.email+" "+city.password+" "+city.name+" "+city.isVerify);
				mv.addObject("isUser",city.isVerify);
				mv.addObject("user_email",city.email);
				mv.addObject("user_name",city.name);
				mv.addObject("image_url",city.image);
				mv.addObject("notice_title",and.title);
				mv.addObject("notice_message",and.message);
				
			}else {
				System.out.println("No such document!");
			}

		} 
	
	return mv;

	}

	@RequestMapping("/signup")
	public ModelAndView signup(userInfo ulog) {

		ModelAndView mv = new ModelAndView();
		CollectionReference employeeCR = db.getFirebase().collection("emplyee");
		employeeCR.document(String.valueOf(ulog.email)).set(ulog);
		mv.setViewName("index");
		return mv;

	}
	
	@RequestMapping("/notice")
	public ModelAndView AddNotice(AddNotices addnotice) {

		ModelAndView mv = new ModelAndView();
		CollectionReference employeeCR = db.getFirebase().collection("notice");
		employeeCR.document(String.valueOf(addnotice.title)).set(addnotice);
		mv.setViewName("index");
		return mv;

	}
}
