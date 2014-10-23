package com.klm.tcs.flatfile;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

public class MultiLineReader implements ResourceAwareItemReaderItemStream<String> {

	private ResourceAwareItemReaderItemStream<String> delegate;

	public String read() throws Exception {
		StringBuilder t = null;
		for (String line = null; (line = this.delegate.read()) != null;) {
			if (StringUtils.startsWith(line, "20") && StringUtils.endsWith(line, ":Envelope>")) {
				return line;
			}
			if (null == t) {
				t = new StringBuilder();
			}
			if (StringUtils.endsWith(line, ":Envelope>")) {
				t.append(line);
				return t.toString();
			} else {
				t.append(line);
			}
		}
		return null;
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

	public void setResource(Resource resource) {
		this.delegate.setResource(resource);
	}
}