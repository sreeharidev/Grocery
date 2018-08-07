package com.noname.grocery.cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.Statement;
import com.noname.grocery.Response;
import com.noname.grocery.product.ProductDAO;
 
//TODO handle exceptions
@Component
public class CartDAO {

	JdbcTemplate jdbcTemplate;
	@Autowired
	ProductDAO productDAO;
	public CartDAO(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	public long getCartId(String userId){
		String sql = "select cartId from usersCart where userId=? and status ='INIT'";
		long cartId = 0;
		try{
		 cartId = (Long)jdbcTemplate.queryForObject(
				sql, new Object[] { userId }, Long.class);
		}catch(EmptyResultDataAccessException em){
			cartId = 0;
		}
		if(cartId == 0){
			final KeyHolder holder = new GeneratedKeyHolder();
			String userQuery = "INSERT INTO usersCart (userId) VALUES (?)";
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(userQuery.toString(), Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, userId);
					return ps;
				}
			}, holder);
			cartId = holder.getKey().longValue();
			 
		}
		return cartId;

	}
    public int getCartCount(String userId){
    	String sql = "select count(*) count from cart c where cartId in (select cartId from usersCart where userId=?)";
    	int  count = 0;
		try{
			count = (Integer)jdbcTemplate.queryForObject(
				sql, new Object[] { userId }, Integer.class);
		}catch(EmptyResultDataAccessException em){
			count = 0;
		}
		return count;
    }
	public Response<Object> addToCart(String userId,long ppId,int count){
		
		long cartId = getCartId(userId);
		Response<Object> response = new Response<>();
		String sql = "insert into Cart(cartId,ppId,count) VALUES (?,?,?)";
		try {
			jdbcTemplate.update(sql, new Object[] {cartId,ppId,count});
		} catch (DataAccessException de) {
			 
			 

		}
		return response;
	}
	 
	//TODO move to properties email client
	private JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("sreeharips@gmail.com");
	    mailSender.setPassword("Rekha@1311");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
	//TODO move to different package
	public Response<Object> placeOrder(Checkout checkout){
		Response<Object> response = new Response<>();
		SimpleMailMessage message = new SimpleMailMessage(); 
		
        message.setTo(checkout.getEmail());
        
        message.setSubject("You have placed an order with GroBuck"); 
        message.setText(productDAO.getCartAsText(checkout));
        getJavaMailSender().send(message);
		return response;
	}
}
