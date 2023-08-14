package CSVCreator.CSVCreator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.*;

import com.sun.jdi.Value;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class CSVCreatorClass {

	public static void main(String[] args) {
		
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("Enter Path location to file:  ");
			String fileLocation = scanner.nextLine();
			
			System.out.print("Input File name:  ");
			String fileName = scanner.nextLine();
			
	        System.out.print("Enter 'Name' value: ");
	        String name = scanner.nextLine();
	        
	        System.out.print("Enter 'Age' value:  ");
	        String age = scanner.nextLine();
	        
	        System.out.print("How many files do you want?:  ");
	        int numberOfFiles = scanner.nextInt();
	        
	        scanner.close();
		
	        
		//TODO
		//Create Scanner to input data from cmd - DONE
		//Replace parameters with Scanner inputs - DONE
		//Select location for files creation - DONE
		//Create executable - DONE
	    //Add input validations - 
	    //Consider headers parametrization - 
	        
	    //Templates, engines: txt crudo, csv, excel, word. 
	    //Cada uno tenga sus placeholders 
	    //Arrancar con el txt crudo. Ejecutable recibe 1 solo parametro (un archivo de configuracion
	    //que contiene: primera linea :1 archivo de template y después key-Value. Key=placeholder, value=textoCrudo o un generador.
	    //generador= incremental o un 
	   //pile name pattern, patron estilo printf 
	        
		String[] data = {name,age};
		createCSVFilesParemeterized(data,fileName,numberOfFiles,fileLocation); 
		System.out.println("System Ran successfully");
	}
	
	//TODO
	public static void createCSVFilesParemeterized(String[] content,String fileName, int numberOfFilesRequested,String fileLocation) {
		
		String[] headers = { "id", "Name", "Age" };
		String[] data = {content[0], content[1]};
		
		int startingPoint = 1;
		while(startingPoint <= numberOfFilesRequested) {
			
			try (Workbook workbook = new HSSFWorkbook()) {
				CreationHelper creationHelper = workbook.getCreationHelper();
				//tab name
				Sheet sheet = workbook.createSheet("fileName "+startingPoint+"");
				
				// Create header row with column titles
				Row headerRow = sheet.createRow(0);
				for (int i = 0; i < headers.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(creationHelper.createRichTextString(headers[i]));
				}
				
				// Generate unique id data for ID column
				//hashSet has records of unique values for ID use
				Set<Integer> usedIds = new HashSet<>();
				
				for (int rowNum = 1; rowNum <= 100; rowNum++) {
					Row row = sheet.createRow(rowNum);
					
					int newId;
					do {
						newId = (int) (Math.random() * 100) + 1; // Random number 
					} while (usedIds.contains(newId));
					usedIds.add(newId);
					Cell idCell = row.createCell(0);
					idCell.setCellValue(newId);

					for (int i = 1; i < headers.length; i++) {
						Cell cell = row.createCell(i);
						cell.setCellValue(creationHelper.createRichTextString(data[i-1]));
					}
				}
				
				try (FileOutputStream fileOut = new FileOutputStream(new File(fileLocation,"fileName"+startingPoint+".csv"))) {
					workbook.write(fileOut);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			startingPoint++;
		}
	}
}
