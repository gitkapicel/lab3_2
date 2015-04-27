package edu.iis.mto.staticmock;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.junit.Assert.*;
import edu.iis.mto.staticmock.reader.FileNewsReader;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.reflect.Whitebox.getField;

import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ConfigurationLoader.class, NewsReaderFactory.class })
public class NewsLoaderTest {

	@SuppressWarnings("unchecked" )
	@Test
	public void newsloader_expected_one_public_news()
			throws IllegalArgumentException, IllegalAccessException {

		// GIVEN:
		IncomingNews incomingNews = new IncomingNews();
		IncomingInfo incomingInfo = new IncomingInfo("subscriptionType",
				SubsciptionType.NONE);
		incomingNews.add(incomingInfo);

		// mock Configuration
		Configuration configuration = mock(Configuration.class);
		when(configuration.getReaderType()).thenReturn("File");

		// mock ConfigurationLoader
		mockStatic(ConfigurationLoader.class);
		ConfigurationLoader configurationLoader = mock(ConfigurationLoader.class);
		when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);
		when(configurationLoader.loadConfiguration()).thenReturn(configuration);

		// mock FileNewsReader
		FileNewsReader fileNewsReader = mock(FileNewsReader.class);
		when(fileNewsReader.read()).thenReturn(incomingNews);

		// mock NewsReaderFactory
		mockStatic(NewsReaderFactory.class);
		when(NewsReaderFactory.getReader((String) Mockito.any())).thenReturn(fileNewsReader);

		NewsLoader newsLoader = new NewsLoader();

		// WHEN:
		PublishableNews publishableNews = newsLoader.loadNews();
		Field field = getField(PublishableNews.class, "publicContent");
		List<String> testPublicContent = (List<String>) field.get(publishableNews);
		
		
	
		Field field2 = getField(PublishableNews.class, "subscribentContent");
		List<String> testSubscribentContent = (List<String>) field2.get(publishableNews);
		
		
		
		// THEN:
		assertEquals(testPublicContent.size(), 1);
		assertEquals(testSubscribentContent.size(), 0);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void loadNews_expected_publicContent()
			throws IllegalArgumentException, IllegalAccessException {

		// GIVEN:
		IncomingNews incomingNews = new IncomingNews();
		IncomingInfo someInfo = new IncomingInfo("publicType",
				SubsciptionType.NONE);
		incomingNews.add(someInfo);

		// mock Configuration
		Configuration configuration = mock(Configuration.class);
		when(configuration.getReaderType()).thenReturn("File");

		// mock ConfigurationLoader
		mockStatic(ConfigurationLoader.class);
		ConfigurationLoader configurationLoader = mock(ConfigurationLoader.class);
		when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);
		when(configurationLoader.loadConfiguration()).thenReturn(configuration);

		// mock FileNewsReader
		FileNewsReader fileReader = mock(FileNewsReader.class);
		when(fileReader.read()).thenReturn(incomingNews);

		// mock NewsReaderFactory
		mockStatic(NewsReaderFactory.class);
		when(NewsReaderFactory.getReader((String) Mockito.any())).thenReturn(
				fileReader);

		NewsLoader newsLoader = new NewsLoader();

		// WHEN:
		PublishableNews publishableNews = newsLoader.loadNews();
		Field field = getField(PublishableNews.class, "publicContent");
		List<String> testPublicContent = (List<String>) field
				.get(publishableNews);

		// THEN:
		assertEquals(testPublicContent.size(), 1);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void loadNews_expectedSubscribentContent()
			throws IllegalArgumentException, IllegalAccessException {

		// GIVEN:
		IncomingNews incomingNews = new IncomingNews();
		IncomingInfo someInfo = new IncomingInfo("subscriptionType",
				SubsciptionType.A);
		incomingNews.add(someInfo);

		// mock Configuration
		Configuration configuration = mock(Configuration.class);
		when(configuration.getReaderType()).thenReturn("File");

		// mock ConfigurationLoader
		mockStatic(ConfigurationLoader.class);
		ConfigurationLoader configurationLoader = mock(ConfigurationLoader.class);
		when(ConfigurationLoader.getInstance()).thenReturn(configurationLoader);
		when(configurationLoader.loadConfiguration()).thenReturn(configuration);

		// mock FileNewsReader
		FileNewsReader fileReader = mock(FileNewsReader.class);
		when(fileReader.read()).thenReturn(incomingNews);

		// mock NewsReaderFactory
		mockStatic(NewsReaderFactory.class);
		when(NewsReaderFactory.getReader((String) Mockito.any())).thenReturn(
				fileReader);

		NewsLoader newsLoader = new NewsLoader();

		// WHEN:
		PublishableNews publishableNews = newsLoader.loadNews();
		Field field = getField(PublishableNews.class, "subscribentContent");
		List<String> testSubscribentContent = (List<String>) field
				.get(publishableNews);

		// THEN:
		assertEquals(testSubscribentContent.size(), 1);
	}
}