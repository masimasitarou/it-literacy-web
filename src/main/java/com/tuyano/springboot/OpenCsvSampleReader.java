package com.tuyano.springboot;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class OpenCsvSampleReader {
	public static void main(String[] args) throws IOException, CsvValidationException {
        FileReader fileReader = null;
        CSVReader csvReader = null;
        try {
              fileReader = new FileReader("testfile.csv");
              csvReader = new CSVReader(fileReader);
              String[] record = null;
              while ((record = csvReader.readNext()) != null) {
                    System.out.println(record[0] + "," + record[1] + "," + record[2] + "," + record[3]);
              }
        } finally {
              if (fileReader != null) {
                    fileReader.close();
              }
              if (csvReader != null) {
                    csvReader.close();
              }
        }
  }
}
