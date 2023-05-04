package com.trueid.aml.algo.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVWriter;
import com.trueid.aml.algo.data.MatchedData;
import com.trueid.aml.algo.data.PosidexData;
import com.trueid.aml.algo.data.PythonJaroData;
import com.trueid.aml.algo.data.PythonLevenData;
import com.trueid.aml.algo.data.PythonQratioData;
import com.trueid.aml.algo.data.PythonSetRatioData;
import com.trueid.aml.algo.data.PythonSortRationData;
import com.trueid.aml.algo.data.RosetteData;
import com.trueid.aml.casemanagement.controller.CaseManagmentController;

import lombok.Data;

public class MatchingResultsExporter {
	
	private static final Logger log = LoggerFactory.getLogger(MatchingResultsExporter.class);

	public String[] rosette(List<RosetteData> rData, Map<Long, ColumnData> colDataMap,
			List<DataRank> dataOrder, String name) {
		if (rData != null) {
			for (RosetteData p : rData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.rData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.rData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}
		Collections.sort(dataOrder, new Comparator<DataRank>() {

			@Override
			public int compare(DataRank o1, DataRank o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

		String rosetteRow = printRow(dataOrder, colDataMap, "ROSETTE");

		rosetteRow = "ROSETTE," + name + "," + rosetteRow;
		return rosetteRow.split(",");
	}

	public String[] rosetteList1(List<RosetteData> rData, Map<Long, ColumnData> colDataMap,
			List<DataRank> dataOrder, String name) {
		if (rData != null) {
			for (RosetteData p : rData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.rData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.rData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}
		Collections.sort(dataOrder, new Comparator<DataRank>() {

			@Override
			public int compare(DataRank o1, DataRank o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

		String rosetteRow = printRow(dataOrder, colDataMap, "ROSETTE");

		rosetteRow = "ROSETTE-ENG," + name + "," + rosetteRow;
		return rosetteRow.split(",");
	}

	public List<PosidexData> posidex(List<PosidexData> pData, Map<Long, ColumnData> colDataMap,
			List<DataRank> dataOrder) {

		if (pData != null) {
			for (PosidexData p : pData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.pData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}
		Collections.sort(pData, new Comparator<PosidexData>() {

			@Override
			public int compare(PosidexData o1, PosidexData o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

	


		return pData;
	}

	public String[] jaro(List<PythonJaroData> pJaroData, Map<Long, ColumnData> colDataMap,
			List<DataRank> dataOrder, String name) {

		if (pJaroData != null) {
			for (PythonJaroData p : pJaroData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pJaro = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pJaro = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}
		Collections.sort(dataOrder, new Comparator<DataRank>() {

			@Override
			public int compare(DataRank o1, DataRank o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

		String jaroRow = printRow(dataOrder, colDataMap, "JARO");
		jaroRow = "JARO," + name + "," + jaroRow;
		return jaroRow.split(",");
	}

	public String[] leven(List<PythonLevenData> pLevenData, Map<Long, ColumnData> colDataMap,
			List<DataRank> dataOrder, String name) {

		if (pLevenData != null) {
			for (PythonLevenData p : pLevenData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pLenen = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pLenen = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}
		Collections.sort(dataOrder, new Comparator<DataRank>() {

			@Override
			public int compare(DataRank o1, DataRank o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

		String levenRow = printRow(dataOrder, colDataMap, "LEVENSHTEIN");

		levenRow = "LEVENSHTEIN," + name + "," + levenRow;
		return levenRow.split(",");
	}

	public String[] qRatio(List<PythonQratioData> pQrData, Map<Long, ColumnData> colDataMap,
			List<DataRank> dataOrder, String name) {

		if (pQrData != null) {
			for (PythonQratioData p : pQrData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pQr = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pQr = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}
		Collections.sort(dataOrder, new Comparator<DataRank>() {

			@Override
			public int compare(DataRank o1, DataRank o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

		String qRationRow = printRow(dataOrder, colDataMap, "QRATIO");
		qRationRow = "QRATIO," + name + "," + qRationRow;
		return qRationRow.split(",");
	}


	public List<String[]> rosetteDataProcess(List<RosetteData> rData, List<PosidexData> pData,
			List<PythonJaroData> pJaroData, List<PythonLevenData> pLevenData, List<PythonQratioData> pQrData,
			Map<Long, ColumnData> colDataMap, List<DataRank> dataOrder, String name) {

		if (rData != null) {
			for (RosetteData p : rData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.rData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.rData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pData != null) {
			for (PosidexData p : pData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.pData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pJaroData != null) {
			for (PythonJaroData p : pJaroData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pJaro = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pJaro = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pLevenData != null) {
			for (PythonLevenData p : pLevenData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pLenen = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pLenen = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pQrData != null) {
			for (PythonQratioData p : pQrData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pQr = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pQr = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		Collections.sort(dataOrder, new Comparator<DataRank>() {

			@Override
			public int compare(DataRank o1, DataRank o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

		return setDataRosette(dataOrder, colDataMap, name);

	}


	public List<String[]> posidexDataProcess(List<RosetteData> rData, List<PosidexData> pData,
			List<PythonJaroData> pJaroData, List<PythonLevenData> pLevenData, List<PythonQratioData> pQrData,
			Map<Long, ColumnData> colDataMap, List<DataRank> dataOrder, String name) {

		if (pData != null) {
			for (PosidexData p : pData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.pData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (rData != null) {
			for (RosetteData p : rData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.rData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.rData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pJaroData != null) {
			for (PythonJaroData p : pJaroData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pJaro = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pJaro = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pLevenData != null) {
			for (PythonLevenData p : pLevenData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pLenen = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pLenen = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pQrData != null) {
			for (PythonQratioData p : pQrData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pQr = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pQr = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		Collections.sort(dataOrder, new Comparator<DataRank>() {

			@Override
			public int compare(DataRank o1, DataRank o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

		return setData(dataOrder, colDataMap, name);

	}

	public List<String[]> jaroDataProcess(List<RosetteData> rData, List<PosidexData> pData,
			List<PythonJaroData> pJaroData, List<PythonLevenData> pLevenData, List<PythonQratioData> pQrData,
			Map<Long, ColumnData> colDataMap, List<DataRank> dataOrder, String name) {

		if (pJaroData != null) {
			for (PythonJaroData p : pJaroData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pJaro = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.pJaro = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (rData != null) {
			for (RosetteData p : rData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.rData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.rData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pData != null) {
			for (PosidexData p : pData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pLevenData != null) {
			for (PythonLevenData p : pLevenData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pLenen = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pLenen = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pQrData != null) {
			for (PythonQratioData p : pQrData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pQr = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pQr = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		Collections.sort(dataOrder, new Comparator<DataRank>() {

			@Override
			public int compare(DataRank o1, DataRank o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

		return setDataJaro(dataOrder, colDataMap, name);
	}

	public List<String[]> levenDataProcess(List<RosetteData> rData, List<PosidexData> pData,
			List<PythonJaroData> pJaroData, List<PythonLevenData> pLevenData, List<PythonQratioData> pQrData,
			Map<Long, ColumnData> colDataMap, List<DataRank> dataOrder, String name) {

		if (pLevenData != null) {
			for (PythonLevenData p : pLevenData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pLenen = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.pLenen = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (rData != null) {
			for (RosetteData p : rData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.rData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.rData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pData != null) {
			for (PosidexData p : pData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pJaroData != null) {
			for (PythonJaroData p : pJaroData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pJaro = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pJaro = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pQrData != null) {
			for (PythonQratioData p : pQrData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pQr = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pQr = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		Collections.sort(dataOrder, new Comparator<DataRank>() {

			@Override
			public int compare(DataRank o1, DataRank o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

		return setDataLeven(dataOrder, colDataMap, name);
	}

	public List<String[]> qRatioDataProcess(List<RosetteData> rData, List<PosidexData> pData,
			List<PythonJaroData> pJaroData, List<PythonLevenData> pLevenData, List<PythonQratioData> pQrData,
			Map<Long, ColumnData> colDataMap, List<DataRank> dataOrder, String name) {

		if (pQrData != null) {
			for (PythonQratioData p : pQrData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pQr = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.pQr = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (rData != null) {
			for (RosetteData p : rData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.rData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its
				cData = new ColumnData();
				cData.rData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pData != null) {
			for (PosidexData p : pData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pData = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pData = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pJaroData != null) {
			for (PythonJaroData p : pJaroData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pJaro = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pJaro = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		if (pLevenData != null) {
			for (PythonLevenData p : pLevenData) {
				ColumnData cData = colDataMap.get(p.getUid());
				if (cData != null) {
					cData.pLenen = p;
					continue;
				}

				// if uuid is not found in the map, create new column and add find its position
				cData = new ColumnData();
				cData.pLenen = p;
				colDataMap.put(p.getUid(), cData);
				DataRank dRank = new DataRank(p.getUid(), p.getScore());
				dataOrder.add(dRank);
			}
		}

		Collections.sort(dataOrder, new Comparator<DataRank>() {

			@Override
			public int compare(DataRank o1, DataRank o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});

		return setDataQratio(dataOrder, colDataMap, name);

	}

	public void export(Map<?, ?> dataMap, String requestedName,String arabicName) {

		ArrayList<DataRank> dataOrder = new ArrayList<>();
		HashMap<Long, ColumnData> colDataMap = new HashMap<>();


		List<PosidexData> pData = (List<PosidexData>) dataMap.get("POSIDEX");

		dataOrder = new ArrayList<>();
		colDataMap = new HashMap<>();

		dataOrder = new ArrayList<>();
		colDataMap = new HashMap<>();
		colDataMap = new HashMap<>();

		dataOrder = new ArrayList<>();
		colDataMap = new HashMap<>();

		dataOrder = new ArrayList<>();
		colDataMap = new HashMap<>();

		dataOrder = new ArrayList<>();
		colDataMap = new HashMap<>();

		dataOrder = new ArrayList<>();
		colDataMap = new HashMap<>();
		
		dataOrder = new ArrayList<>();
		colDataMap = new HashMap<>();
		List<PosidexData> posidex = posidex(pData, colDataMap, dataOrder);
		

		dataOrder = new ArrayList<>();
		colDataMap = new HashMap<>();

		dataOrder = new ArrayList<>();
		colDataMap = new HashMap<>();

		dataOrder = new ArrayList<>();
		colDataMap = new HashMap<>();

		log.info("\nFinal CSV Data  ");

		try {
			storeDataIntoExcel(posidex, requestedName,arabicName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String[]> setDataRosette(List<DataRank> dataOrder, Map<Long, ColumnData> colDataMap,
			String requestedName) {

		String rosetteRow = printRow(dataOrder, colDataMap, "ROSETTE");
		String posidexRow = printRow(dataOrder, colDataMap, "POSIDEX");
		String jaroRow = printRow(dataOrder, colDataMap, "JARO");
		String levenRow = printRow(dataOrder, colDataMap, "LEVENSHTEIN");
		String qRationRow = printRow(dataOrder, colDataMap, "QRATIO");
		String setRatioRow = printRow(dataOrder, colDataMap, "SETRATIO");
		String sortRationRow = printRow(dataOrder, colDataMap, "SORTRATIO");


		requestedName = requestedName + ",";

		posidexRow = "POSIDEX," + requestedName + posidexRow;
		rosetteRow = "ROSETTE," + requestedName + rosetteRow;
		jaroRow = "JARO," + requestedName + jaroRow;
		levenRow = "LEVENSHTEIN," + requestedName + levenRow;
		qRationRow = "QRATIO," + requestedName + qRationRow;
		setRatioRow = "SETRATIO," + requestedName + setRatioRow;
		sortRationRow = "SORTRATIO," + requestedName + sortRationRow;

		String[] arrayRosette = rosetteRow.split(",");
		String[] arrayPosidex = posidexRow.split(",");
		String[] arrayJaro = jaroRow.split(",");
		String[] arrayLeven = levenRow.split(",");
		String[] arrayQratio = qRationRow.split(",");

		String[] columns = { "Algorithm", " Screened Name", " matched data" };
		String[] emptyVal = { "" };

		List<String[]> listData = new ArrayList<>();
		listData.add(columns);
		listData.add(emptyVal);
		listData.add(arrayRosette);
		listData.add(arrayPosidex);
		listData.add(arrayJaro);
		listData.add(arrayLeven);
		listData.add(arrayQratio);

		return listData;

	}

	public List<String[]> setDataRosette1(List<DataRank> dataOrder, Map<Long, ColumnData> colDataMap,
			String requestedName) {

		String rosetteRow1 = printRow(dataOrder, colDataMap, "ROSETTE");
		String posidexRow = printRow(dataOrder, colDataMap, "POSIDEX");
		String jaroRow = printRow(dataOrder, colDataMap, "JARO");
		String levenRow = printRow(dataOrder, colDataMap, "LEVENSHTEIN");
		String qRationRow = printRow(dataOrder, colDataMap, "QRATIO");
		String setRatioRow = printRow(dataOrder, colDataMap, "SETRATIO");
		String sortRationRow = printRow(dataOrder, colDataMap, "SORTRATIO");


		requestedName = requestedName + ",";

		posidexRow = "POSIDEX," + requestedName + posidexRow;
		rosetteRow1 = "ROSETTE-ENG," + requestedName + rosetteRow1;
		jaroRow = "JARO," + requestedName + jaroRow;
		levenRow = "LEVENSHTEIN," + requestedName + levenRow;
		qRationRow = "QRATIO," + requestedName + qRationRow;
		setRatioRow = "SETRATIO," + requestedName + setRatioRow;
		sortRationRow = "SORTRATIO," + requestedName + sortRationRow;

		String[] arrayRosette1 = rosetteRow1.split(",");
		String[] arrayPosidex = posidexRow.split(",");
		String[] arrayJaro = jaroRow.split(",");
		String[] arrayLeven = levenRow.split(",");
		String[] arrayQratio = qRationRow.split(",");

		String[] columns = { "Algorithm", " Screened Name", " matched data" };
		String[] emptyVal = { "" };

		List<String[]> listData = new ArrayList<>();
		listData.add(columns);
		listData.add(emptyVal);
		listData.add(arrayRosette1);
		listData.add(arrayPosidex);
		listData.add(arrayJaro);
		listData.add(arrayLeven);
		listData.add(arrayQratio);

		return listData;

	}

	public List<String[]> setData(List<DataRank> dataOrder, Map<Long, ColumnData> colDataMap,
			String requestedName) {

		String rosetteRow = printRow(dataOrder, colDataMap, "ROSETTE");
		String posidexRow = printRow(dataOrder, colDataMap, "POSIDEX");
		String jaroRow = printRow(dataOrder, colDataMap, "JARO");
		String levenRow = printRow(dataOrder, colDataMap, "LEVENSHTEIN");
		String qRationRow = printRow(dataOrder, colDataMap, "QRATIO");
		String setRatioRow = printRow(dataOrder, colDataMap, "SETRATIO");
		String sortRationRow = printRow(dataOrder, colDataMap, "SORTRATIO");


		requestedName = requestedName + ",";

		posidexRow = "POSIDEX," + requestedName + posidexRow;
		rosetteRow = "ROSETTE," + requestedName + rosetteRow;
		jaroRow = "JARO," + requestedName + jaroRow;
		levenRow = "LEVENSHTEIN," + requestedName + levenRow;
		qRationRow = "QRATIO," + requestedName + qRationRow;
		setRatioRow = "SETRATIO," + requestedName + setRatioRow;
		sortRationRow = "SORTRATIO," + requestedName + sortRationRow;

		String[] arrayRosette = rosetteRow.split(",");
		String[] arrayPosidex = posidexRow.split(",");
		String[] arrayJaro = jaroRow.split(",");
		String[] arrayLeven = levenRow.split(",");
		String[] arrayQratio = qRationRow.split(",");

		String[] emptyVal = { "" };

		List<String[]> listData = new ArrayList<>();
		//
		listData.add(emptyVal);
		listData.add(arrayPosidex);
		listData.add(arrayRosette);
		listData.add(arrayJaro);
		listData.add(arrayLeven);
		listData.add(arrayQratio);

		return listData;

	}

	public List<String[]> setDataDef(List<DataRank> dataOrder, Map<Long, ColumnData> colDataMap,
			String requestedName) {

		String rosetteRow = printRow(dataOrder, colDataMap, "ROSETTE");
		String posidexRow = printRow(dataOrder, colDataMap, "POSIDEX");
		String jaroRow = printRow(dataOrder, colDataMap, "JARO");
		String levenRow = printRow(dataOrder, colDataMap, "LEVENSHTEIN");
		String qRationRow = printRow(dataOrder, colDataMap, "QRATIO");
		String setRatioRow = printRow(dataOrder, colDataMap, "SETRATIO");
		String sortRationRow = printRow(dataOrder, colDataMap, "SORTRATIO");


		requestedName = requestedName + ",";

		posidexRow = "POSIDEX," + requestedName + posidexRow;
		rosetteRow = "ROSETTE," + requestedName + rosetteRow;
		jaroRow = "JARO," + requestedName + jaroRow;
		levenRow = "LEVENSHTEIN," + requestedName + levenRow;
		qRationRow = "QRATIO," + requestedName + qRationRow;
		setRatioRow = "SETRATIO," + requestedName + setRatioRow;
		sortRationRow = "SORTRATIO," + requestedName + sortRationRow;

		String[] arrayRosette = rosetteRow.split(",");
		String[] arrayPosidex = posidexRow.split(",");
		String[] arrayJaro = jaroRow.split(",");
		String[] arrayLeven = levenRow.split(",");
		String[] arrayQratio = qRationRow.split(",");

		String[] emptyVal = { "" };

		List<String[]> listData = new ArrayList<>();
		listData.add(emptyVal);
		listData.add(emptyVal);
		listData.add(arrayRosette);
		listData.add(arrayPosidex);
		listData.add(arrayJaro);
		listData.add(arrayLeven);
		listData.add(arrayQratio);

		return listData;

	}

	public List<String[]> setDataJaro(List<DataRank> dataOrder, Map<Long, ColumnData> colDataMap,
			String requestedName) {

		String rosetteRow = printRow(dataOrder, colDataMap, "ROSETTE");
		String posidexRow = printRow(dataOrder, colDataMap, "POSIDEX");
		String jaroRow = printRow(dataOrder, colDataMap, "JARO");
		String levenRow = printRow(dataOrder, colDataMap, "LEVENSHTEIN");
		String qRationRow = printRow(dataOrder, colDataMap, "QRATIO");
		String setRatioRow = printRow(dataOrder, colDataMap, "SETRATIO");
		String sortRationRow = printRow(dataOrder, colDataMap, "SORTRATIO");


		requestedName = requestedName + ",";

		posidexRow = "POSIDEX," + requestedName + posidexRow;
		rosetteRow = "ROSETTE," + requestedName + rosetteRow;
		jaroRow = "JARO," + requestedName + jaroRow;
		levenRow = "LEVENSHTEIN," + requestedName + levenRow;
		qRationRow = "QRATIO," + requestedName + qRationRow;
		setRatioRow = "SETRATIO," + requestedName + setRatioRow;
		sortRationRow = "SORTRATIO," + requestedName + sortRationRow;

		String[] arrayRosette = rosetteRow.split(",");
		String[] arrayPosidex = posidexRow.split(",");
		String[] arrayJaro = jaroRow.split(",");
		String[] arrayLeven = levenRow.split(",");
		String[] arrayQratio = qRationRow.split(",");

		String[] emptyVal = { "" };

		List<String[]> listData = new ArrayList<>();
		listData.add(emptyVal);
		listData.add(emptyVal);
		listData.add(arrayJaro);
		listData.add(arrayRosette);
		listData.add(arrayPosidex);
		listData.add(arrayLeven);
		listData.add(arrayQratio);

		return listData;

	}

	public List<String[]> setDataLeven(List<DataRank> dataOrder, Map<Long, ColumnData> colDataMap,
			String requestedName) {

		String rosetteRow = printRow(dataOrder, colDataMap, "ROSETTE");
		String posidexRow = printRow(dataOrder, colDataMap, "POSIDEX");
		String jaroRow = printRow(dataOrder, colDataMap, "JARO");
		String levenRow = printRow(dataOrder, colDataMap, "LEVENSHTEIN");
		String qRationRow = printRow(dataOrder, colDataMap, "QRATIO");
		String setRatioRow = printRow(dataOrder, colDataMap, "SETRATIO");
		String sortRationRow = printRow(dataOrder, colDataMap, "SORTRATIO");


		requestedName = requestedName + ",";

		posidexRow = "POSIDEX," + requestedName + posidexRow;
		rosetteRow = "ROSETTE," + requestedName + rosetteRow;
		jaroRow = "JARO," + requestedName + jaroRow;
		levenRow = "LEVENSHTEIN," + requestedName + levenRow;
		qRationRow = "QRATIO," + requestedName + qRationRow;
		setRatioRow = "SETRATIO," + requestedName + setRatioRow;
		sortRationRow = "SORTRATIO," + requestedName + sortRationRow;

		String[] arrayRosette = rosetteRow.split(",");
		String[] arrayPosidex = posidexRow.split(",");
		String[] arrayJaro = jaroRow.split(",");
		String[] arrayLeven = levenRow.split(",");
		String[] arrayQratio = qRationRow.split(",");

		String[] emptyVal = { "" };

		List<String[]> listData = new ArrayList<>();
		listData.add(emptyVal);
		listData.add(emptyVal);
		listData.add(arrayLeven);
		listData.add(arrayRosette);
		listData.add(arrayPosidex);
		listData.add(arrayJaro);
		listData.add(arrayQratio);

		return listData;

	}

	public List<String[]> setDataQratio(List<DataRank> dataOrder, Map<Long, ColumnData> colDataMap,
			String requestedName) {

		String rosetteRow = printRow(dataOrder, colDataMap, "ROSETTE");
		String posidexRow = printRow(dataOrder, colDataMap, "POSIDEX");
		String jaroRow = printRow(dataOrder, colDataMap, "JARO");
		String levenRow = printRow(dataOrder, colDataMap, "LEVENSHTEIN");
		String qRationRow = printRow(dataOrder, colDataMap, "QRATIO");
		String setRatioRow = printRow(dataOrder, colDataMap, "SETRATIO");
		String sortRationRow = printRow(dataOrder, colDataMap, "SORTRATIO");


		requestedName = requestedName + ",";

		posidexRow = "POSIDEX," + requestedName + posidexRow;
		rosetteRow = "ROSETTE," + requestedName + rosetteRow;
		jaroRow = "JARO," + requestedName + jaroRow;
		levenRow = "LEVENSHTEIN," + requestedName + levenRow;
		qRationRow = "QRATIO," + requestedName + qRationRow;
		setRatioRow = "SETRATIO," + requestedName + setRatioRow;
		sortRationRow = "SORTRATIO," + requestedName + sortRationRow;

		String[] arrayRosette = rosetteRow.split(",");
		String[] arrayPosidex = posidexRow.split(",");
		String[] arrayJaro = jaroRow.split(",");
		String[] arrayLeven = levenRow.split(",");
		String[] arrayQratio = qRationRow.split(",");

		String[] emptyVal = { "" };

		List<String[]> listData = new ArrayList<>();
		listData.add(emptyVal);
		listData.add(emptyVal);
		listData.add(arrayQratio);
		listData.add(arrayRosette);
		listData.add(arrayPosidex);
		listData.add(arrayJaro);
		listData.add(arrayLeven);

		return listData;

	}

	private void storeDataIntoExcel(List<PosidexData> listData, String requestedName,String arabicName) throws IOException {


		
		PosidexData[] objArray = new PosidexData[listData.size()];
		objArray = listData.toArray(objArray);
		
		Arrays.sort(objArray, new Comparator<PosidexData>() {

			@Override
			public int compare(PosidexData o1, PosidexData o2) {

				if (o1.getScore() - o2.getScore() > 0) {
					return -1;
				} else
					return 1;

			}

		});
		

		
	
		// workbook object
        XSSFWorkbook workbook = new XSSFWorkbook();
        
     // spreadsheet object
        XSSFSheet spreadsheet
            = workbook.createSheet(" WorldCheck Data ");
        
     // creating a row object
        XSSFRow row;
        
        
 
     // This data needs to be written (Object[])
        Map<String, PosidexData[]> wc = new TreeMap<>();
        
        
       for(int i=0;i<objArray.length;i++) {
        	wc.put(objArray[i].getUid()+"", new PosidexData[] {objArray[i]});
        	
        }
       
        log.info("Map Data -- ",wc);
        
        
        Set<String> keyid = new HashSet<>();
        
        for(int i=0;i< wc.size();i++) {
        	keyid = wc.keySet();
        
            
        log.info("Key Set ",keyid);
        
        int rowid = 0;
  
        // writing the data into the sheets...
        
        int cellid1 = 0;
        row = spreadsheet.createRow(rowid++);
        Cell cell0 = row.createCell(cellid1++);
        cell0.setCellValue("Input: Screened Name: "+requestedName);
        
        Cell cell01 = row.createCell(cellid1++);
        cell01.setCellValue("Arabic Name: "+arabicName);
        
        row = spreadsheet.createRow(rowid++);
        
        
        int cellid2 = 0;
        row = spreadsheet.createRow(rowid++);
        Cell cell1 = row.createCell(cellid2++);
        cell1.setCellValue("Algorithm");
        
        Cell cell2 = row.createCell(cellid2++);
        cell2.setCellValue(" WorldCheckID");
        
        Cell cell3 = row.createCell(cellid2++);
        cell3.setCellValue("MatchedName");
         
        Cell cell4 = row.createCell(cellid2++);
        cell4.setCellValue("totalMatches");
        
        Cell cell5 = row.createCell(cellid2++);
        cell5.setCellValue("MatchingScore");
        
        Cell cell6 = row.createCell(cellid2++);
        cell6.setCellValue("Manual Verification Status (Correct/Incorrect)");
  
        int matchScore = keyid.size();
        
        for (String key : keyid) {
  
            row = spreadsheet.createRow(rowid++);
            PosidexData[] objectArr = wc.get(key);
            int cellid = 0;
            
           
  
            for (PosidexData obj : objectArr) {            	
                Cell cel = row.createCell(cellid++);
                cel.setCellValue("POSIDEX");
                Cell cell = row.createCell(cellid++);
                cell.setCellValue(obj.getUid());
                Cell cell7 = row.createCell(cellid++);
                cell7.setCellValue(obj.getMatchedName());
                Cell cell8 = row.createCell(cellid++);
                cell8.setCellValue(matchScore);
                Cell cell9 = row.createCell(cellid++);
                cell9.setCellValue(obj.getScore());
            }
        }
        
        try( FileOutputStream out = new FileOutputStream(new File("D:\\WC\\"+requestedName+".xlsx"))) {
            workbook.write(out);
           
        }finally {
        	 workbook.close();
		}
        }
	}

	

	private String printRow(List<DataRank> dataOrderList, Map<Long, ColumnData> dataMap, String algoName) {
		Boolean isFirst = true;
		String dataStr = "";
		for (DataRank dr : dataOrderList) {
			ColumnData cData = dataMap.get(dr.getUuid());
			Object o = cData.getData(algoName);
			if (Boolean.TRUE.equals(isFirst)) {
				isFirst = false;
			} else {
				dataStr += ",";
			}
			if (o != null) {
				dataStr += o;
			} else {
				dataStr += getBlankData();
			}

		}

		return dataStr;

	}


	String getBlankData() {
		return ",,";
	}

	@Data
	class DataRank {
		Long uuid;
		Float score;

		DataRank(Long uuid, Float score) {
			this.uuid = uuid;
			this.score = score;
		}
	}

	@Data
	class ColumnData {
		RosetteData rData;
		PosidexData pData;
		PythonJaroData pJaro;
		PythonLevenData pLenen;
		PythonQratioData pQr;
		PythonSetRatioData pSr;
		PythonSortRationData pSor;

		MatchedData getData(String type) {
			if (rData != null && type.equals(rData.getAlogName()))
				return rData;
			if (pData != null && type.equals(pData.getAlogName()))
				return pData;
			if (pJaro != null && type.equals(pJaro.getAlogName()))
				return pJaro;
			if (pLenen != null && type.equals(pLenen.getAlogName()))
				return pLenen;
			if (pQr != null && type.equals(pQr.getAlogName()))
				return pQr;
			if (pSr != null && type.equals(pSr.getAlogName()))
				return pSr;
			if (pSor != null && type.equals(pSor.getAlogName()))
				return pSor;

			return null;
		}
	}

}
