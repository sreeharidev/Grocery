package com.noname.grocery;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Test {

	public static void main(String[] args) {
		 
        String hashed = BCrypt.hashpw("sreehari", BCrypt.gensalt(12));
        System.out.println("encrypt");
        System.out.println(hashed);
       System.out.println( BCrypt.checkpw("sreehari", "$2a$12$jBsfg8c43fSoH96wQmvNiOIOBmwUjS8eJDQgo7ewXjH0OTfMVH0Ja"));


	}

}
