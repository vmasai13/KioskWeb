package com.klm.tcs.kiosk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.core.io.Resource;

import com.klm.tcs.flatfile.ErrorCode;

public class ErrorWriter implements ResourceAwareItemWriterItemStream<ErrorCode> {
	private ResourceAwareItemWriterItemStream<String> delegate;
	private List<String> errors;
@Override
public void write(List<? extends ErrorCode> items) throws Exception {
	List<String> toWrite = new ArrayList<String>();
	for (ErrorCode rawRecord : items) {
		toWrite.add(rawRecord.getError());
	}
	this.delegate.write(toWrite);
}


public void setErrors(String[] error) {
	this.errors = Arrays.asList(error);
}


public void setDelegate(ResourceAwareItemWriterItemStream<String> delegate) {
	this.delegate = delegate;
}

@Override
public void setResource(Resource resource) {
	this.delegate.setResource(resource);
}

@Override
public void open(ExecutionContext executionContext) throws ItemStreamException {
	this.delegate.open(executionContext);
}

@Override
public void update(ExecutionContext executionContext) throws ItemStreamException {
	this.delegate.update(executionContext);

}

@Override
public void close() throws ItemStreamException {
	this.delegate.close();
}
}
