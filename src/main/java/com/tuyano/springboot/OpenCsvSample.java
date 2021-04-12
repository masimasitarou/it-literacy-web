package com.tuyano.springboot;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

public class OpenCsvSample {
	public static void main(String[] args) throws IOException {
        FileWriter fileWriter = null;
        CSVWriter csvWriter = null;
        try {
              fileWriter = new FileWriter("testfile.csv");
              csvWriter = new CSVWriter(fileWriter);

              // ヘッダー
              List<String> header = new ArrayList<String>();
              header.add("PRIMARY_ID");
              header.add("TITLE_ID");
              header.add("TITLE");
              header.add("MEMBER_MEMO");
              
              csvWriter.writeNext(header.toArray(new String[header.size()]));

              // レコードの作成
              List<String> record = new ArrayList<String>();
              record.add("1");
              record.add("1");
              record.add("サンプルタイトルです");
              record.add("メモのサンプルです");
              csvWriter.writeNext(record.toArray(new String[record.size()]));
              record = new ArrayList<String>();
              record.add("2");
              record.add("2");
              record.add("サンプルタイトルテストです。");
              record.add("メモのテストです");
              csvWriter.writeNext(record.toArray(new String[record.size()]));
        } finally {
              if (csvWriter != null) {
                    csvWriter.close();
              }
        }
  }
}
