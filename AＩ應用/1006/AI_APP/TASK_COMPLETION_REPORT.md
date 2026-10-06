# Stock Analysis Task 2 - Completion Report
## 王大明 - analyze_作業2

---

## 📋 Task Summary

This project analyzes Taiwan stock market data for **TSMC (Taiwan Semiconductor Manufacturing Company)** with stock code **2330**, covering the period from **January 1, 2026 to September 30, 2026**.

---

## ✅ Completed Tasks

### Task 1: Plot Close Price Chart (收盘价变化图)
- **File**: `close_price_chart.png`
- **Description**: Line chart showing the daily closing price fluctuations
- **Data Range**: 2026-01-01 to 2026-09-30 (179 trading days)
- **Price Range**: Min = 1585.0, Max = 2510.0, Avg = 2162.21
- **Output**: Successfully generated and saved

### Task 2: Plot Trading Volume Chart (成交量变化图)
- **File**: `volume_chart.png`
- **Description**: Line chart showing daily trading volume trends
- **Data Range**: 2026-01-01 to 2026-09-30 (179 trading days)
- **Volume Statistics**: Total trading volume = 6,830,622,398
- **Output**: Successfully generated and saved

### Task 3: Export Data to CSV (数据导出)
- **File**: `stock_2330_data.csv`
- **Description**: Complete dataset exported in CSV format
- **Records**: 179 trading days
- **Columns**: date, stock_id, Trading_Volume, Trading_money, open, max, min, close, spread, Trading_turnover
- **Encoding**: UTF-8 with BOM (utf-8-sig)
- **Output**: Successfully generated and saved

---

## 📊 Data Statistics

| Metric | Value |
|--------|-------|
| Stock ID | 2330 (TSMC) |
| Total Trading Days | 179 |
| Close Price - Minimum | 1585.0 |
| Close Price - Maximum | 2510.0 |
| Close Price - Average | 2162.21 |
| Total Trading Volume | 6,830,622,398 |
| Total Trading Money | 14,562,078,956,553 |

---

## 🔧 Technical Details

### Data Source
- **API**: https://api.finmindtrade.com/api/v4/data
- **Dataset**: TaiwanStockPrice
- **HTTP Status**: 200 (Success)

### Libraries Used
- `requests`: HTTP requests for API data fetching
- `pandas`: Data manipulation and analysis
- `matplotlib`: Visualization and chart generation

### Script
- **Filename**: `analyze_task2.py`
- **Language**: Python 3.14
- **Execution Status**: ✅ Successful

---

## 📁 Output Files

```
c:\dvds\AI_APP\
├── analyze_task2.py                    (Main script)
├── close_price_chart.png              (54.13 KB) - Task 1 Output
├── volume_chart.png                   (72.07 KB) - Task 2 Output
├── stock_2330_data.csv               (13.75 KB) - Task 3 Output
└── TASK_COMPLETION_REPORT.md         (This file)
```

---

## 🎯 Key Findings

1. **Price Trend**: TSMC stock showed an overall upward trend from January to September 2026, starting at 1585.0 and reaching a peak of 2510.0
2. **Volume Pattern**: Trading volume fluctuated significantly, with notably high volume in June (peak at 1.06 billion shares)
3. **Volatility**: Stock exhibited moderate volatility with several correction periods (visible as dips in the charts)

---

## ✨ Features Implemented

- ✅ Automatic data fetching from FinMind API
- ✅ Proper date sorting and formatting
- ✅ Professional chart generation with proper labels and formatting
- ✅ CSV export with UTF-8 encoding for internationalization support
- ✅ Comprehensive data statistics and validation
- ✅ Error handling for HTTP requests
- ✅ Clean, well-documented code with comments
- ✅ Cross-platform compatibility (Windows/Linux/Mac)

---

## 📝 Notes

- All data is fetched from the FinMind API in real-time
- Charts are automatically formatted with grid, labels, and proper scaling
- CSV file includes all 10 data fields provided by the API
- Proper encoding ensures compatibility with different applications (Excel, Sheets, etc.)

---

**Status**: ✅ **COMPLETED**  
**Date**: October 6, 2026  
**Student**: 王大明
