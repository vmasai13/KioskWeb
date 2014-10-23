package com.klm.tcs.kiosk;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

public class MultiErrorReader implements ResourceAwareItemReaderItemStream<String> {

	private ResourceAwareItemReaderItemStream<String> delegate;

	

	public void close() throws ItemStreamException {
		this.delegate.close();
	}

	public void open(ExecutionContext executionContext) throws ItemStreamException {
		this.delegate.open(executionContext);
	}

	public void update(ExecutionContext executionContext) throws ItemStreamException {
		this.delegate.update(executionContext);
	}

	public void setResource(Resource resource) {
		this.delegate.setResource(resource);
	}

	public void setDelegate(ResourceAwareItemReaderItemStream<String> delegate) {
		this.delegate = delegate;
	}

	@Override
	public String read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		System.out.println("hihihihih");
			int count310=0;
			int count311=0;
			int count001=0;
			int count102=0;
			int count110=0;
		for (String line = null; (line = this.delegate.read()) != null;) {
			if(StringUtils.contains(line, "ResponseCode : 0310")) {
				return "0310";
				
			}
			if(StringUtils.contains(line, "ResponseCode : 0311")) {
				return "0311";
				
			}
			if(StringUtils.contains(line, "ResponseCode : -001")) {
				return "-001";
				
			}
			if(StringUtils.contains(line, "ResponseCode : 0110")) {
				return "0110";
				
			}
			if(StringUtils.contains(line, "ResponseCode : 0102")) {
				return "0102";
				
			}
		}
		System.out.println("trail"+count310 );
		// TODO Auto-generated method stub
		return  null;
	}

	
}
