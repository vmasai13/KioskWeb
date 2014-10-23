package com.klm.tcs.kiosk;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.core.io.Resource;

public class KioskFlowTraceWriter implements ResourceAwareItemWriterItemStream<RawRecord> {

	private ResourceAwareItemWriterItemStream<String> delegate;

	@Override
	public void write(List<? extends RawRecord> items) throws Exception {
		List<String> toWrite = new ArrayList<String>();
		for (RawRecord rawRecord : items) {
			if (null != rawRecord) {
				if(rawRecord.getFlowView().equals("chipAndPinPayment") || rawRecord.getFlowView().equals("paymentResultPage") || 
						rawRecord.getFlowView().equals("paymentConfirmed") || rawRecord.getFlowView().equals("paymentFailed") ||
						rawRecord.getFlowView().equals("paymentTimeOut") || rawRecord.getFlowView().equals("chipAndPinEnd") ||
						rawRecord.getFlowView().equals("timeoutView")){
					toWrite.add(rawRecord.getRawLine());
				}
			}
		}
		this.delegate.write(toWrite);
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