package com.tuyano.springboot;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCsvReader {
	public static void main(String[] args) throws IOException {
		FileReader fileReader = null;
		//csvファイルを読み込む
		try {
			fileReader = new FileReader("testfile.csv");
			List<CsvDataBean> dataObjList = new CsvToBeanBuilder<CsvDataBean>(fileReader).withType(CsvDataBean.class).build().parse();

			for (CsvDataBean obj : dataObjList) {
				CsvDataBean csvData = new CsvDataBean();
				csvData.setMemberName(obj.getMemberName());
				csvData.setMemberName(obj.getMemberMail());
				csvData.setMemberName(obj.getMemberAge());
				csvData.setMemberName(obj.getMemberMemo());
				
			}
		} finally {
			if (fileReader != null) {
				fileReader.close();
			}
		}
	}
}
