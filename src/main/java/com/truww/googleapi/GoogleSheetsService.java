package com.truww.googleapi;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetsService {

    private static final String APPLICATION_NAME =
            "Google Sheets API Exercise";

    private static final String SPREADSHEET_ID =
            "1ohb09wXcnq7edjCP5WhUKj8qAgcxX3d9_YjfjBmm3V8";

    private static final String RANGE =
            "Class Data";

    public void readSheet() throws IOException, GeneralSecurityException {

        GoogleCredentials credentials;

        ClassPathResource resource =
                new ClassPathResource("creds.json");

        try (InputStream inputStream = resource.getInputStream()) {

            credentials = GoogleCredentials
                    .fromStream(inputStream)
                    .createScoped(
                            Collections.singleton(
                                    SheetsScopes.SPREADSHEETS_READONLY
                            )
                    );
        }

        HttpRequestInitializer requestInitializer =
                new HttpCredentialsAdapter(credentials);

        Sheets sheetsService = new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer
        )
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange response = sheetsService
                .spreadsheets()
                .values()
                .get(SPREADSHEET_ID, RANGE)
                .execute();

        List<List<Object>> rows = response.getValues();

        if (rows == null || rows.isEmpty()) {
            System.out.println("No data found.");
            return;
        }

        for (List<Object> row : rows) {
            System.out.println(row);
        }
    }
}
