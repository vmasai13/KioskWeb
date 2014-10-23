package com.tcs.batch.file;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

public class MultiLineReader implements ResourceAwareItemReaderItemStream<String> {
private ResourceAwareItemReaderItemStream<String> delegate;
	
	/**
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	public String read() throws Exception {
		for (String line = null; (line = this.delegate.read()) != null;) {
			if(line.contains("EMV") && line.contains("ResponseCode")) {
			System.out.println(line);
			return line;
			}
		}
		return "";
	}

	public void setDelegate(ResourceAwareItemReaderItemStream<String> delegate) {
		this.delegate = delegate;
	}

	public void close() throws ItemStreamException {
		this.delegate.close();
	}

	public void open(ExecutionContext executionContext) throws ItemStreamException {
		this.delegate.open(executionContext);
	}

	public void update(ExecutionContext executionContext) throws ItemStreamException {
		this.delegate.update(executionContext);
	}

	@Override
	public void setResource(Resource resource) {
		this.delegate.setResource(resource);
	}
}
