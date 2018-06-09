package de.ksc.news.feed.core.service;

import org.xml.sax.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class NamespaceIgnoringXmlReader implements XMLReader {

    private final XMLReader delegate;

    public NamespaceIgnoringXmlReader() throws ParserConfigurationException, SAXException {

        final SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(false);
        this.delegate = factory.newSAXParser().getXMLReader();
    }

    @Override
    public boolean getFeature(final String name) throws SAXNotRecognizedException, SAXNotSupportedException {

        return this.delegate.getFeature(name);
    }

    @Override
    public void setFeature(final String name, final boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {

        this.delegate.setFeature(name, value);
    }

    @Override
    public Object getProperty(final String name) throws SAXNotRecognizedException, SAXNotSupportedException {

        return this.delegate.getProperty(name);
    }

    @Override
    public void setProperty(final String name, final Object value) throws SAXNotRecognizedException, SAXNotSupportedException {

        this.delegate.setProperty(name, value);
    }

    @Override
    public void setEntityResolver(final EntityResolver resolver) {

        this.delegate.setEntityResolver(resolver);
    }

    @Override
    public EntityResolver getEntityResolver() {

        return this.delegate.getEntityResolver();
    }

    @Override
    public void setDTDHandler(final DTDHandler handler) {

        this.delegate.setDTDHandler(handler);
    }

    @Override
    public DTDHandler getDTDHandler() {

        return this.delegate.getDTDHandler();
    }

    @Override
    public void setContentHandler(final ContentHandler handler) {

        this.delegate.setContentHandler(handler);
    }

    @Override
    public ContentHandler getContentHandler() {

        return this.delegate.getContentHandler();
    }

    @Override
    public void setErrorHandler(final ErrorHandler handler) {

        this.delegate.setErrorHandler(handler);
    }

    @Override
    public ErrorHandler getErrorHandler() {

        return this.delegate.getErrorHandler();
    }

    @Override
    public void parse(final InputSource input) throws IOException, SAXException {

        this.delegate.parse(input);
    }

    @Override
    public void parse(final String systemId) throws IOException, SAXException {

        this.delegate.parse(systemId);
    }
}
