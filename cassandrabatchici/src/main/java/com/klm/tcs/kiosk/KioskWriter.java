package com.klm.tcs.kiosk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemWriterItemStream;
import org.springframework.core.io.Resource;

import com.klm.tcs.util.Constants;

public class KioskWriter implements ResourceAwareItemWriterItemStream<RawRecord> {

	private List<String> kioskids;
	private ResourceAwareItemWriterItemStream<String> delegate;

	@Override
	public void write(List<? extends RawRecord> items) throws Exception {
		List<String> toWrite = new ArrayList<String>();
		for (RawRecord rawRecord : items) {
			if (null != rawRecord) {
				// if (kioskids.contains(rawRecord.getKioskId().trim())) {
				if (rawRecord.getRequestType().equalsIgnoreCase(Constants.MSG_SELL_PRODUCT_REQ)) {
					// if (null != rawRecord.getDepartureStation() &&
					// rawRecord.getDepartureStation().equals("AMS")) {
					if (null != rawRecord.getPaymentGroupCode() && "CHIPPIN".equalsIgnoreCase(rawRecord.getPaymentGroupCode())) {
						toWrite.add(rawRecord.getRawLine());
						// toWrite.add(rawRecord.getKioskId());
						Storage.getInstance().getId().add(rawRecord.getClientTransactionId());
					}
				} else if (rawRecord.getRequestType().equalsIgnoreCase(Constants.MSG_SELL_PRODUCT_RES)) {
					if (Storage.getInstance().getId().contains(rawRecord.getClientTransactionId())) {
						toWrite.add(rawRecord.getRawLine());
					}
				}
				// }
				// }
			}
		}
		this.delegate.write(toWrite);
	}

	public void setKiosklist(String kiosklist) {
		this.kioskids = Arrays.asList(kiosklist.split(","));
	}

	public void setKioskids(String[] kiosks) {
		this.kioskids = Arrays.asList(kiosks);
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