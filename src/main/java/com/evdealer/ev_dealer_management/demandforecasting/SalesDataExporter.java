package com.evdealer.ev_dealer_management.demandforecasting;

import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;

public class SalesDataExporter {

//    @Value("${spring.datasource.url}")
//    private static final String urlFromAppFile;
//
//    @Value("${spring.datasource.username}")
//    private static final String username;
//
//    @Value("${spring.datasource.password}")
//    private static final String password;

    public void exportFromDatabase(String url, String username, String password) throws SQLException, IOException {

        Connection conn = DriverManager.getConnection(url, username, password);
        String query = "SELECT o.dealer_id, o.car_model_id, MONTH(o.created_on) AS month, "
                + "COUNT(o.id) AS pastSales, pd.suggested_sale_price AS price, "
                + "CASE WHEN cm.car_status IN ('FOR_SALE','TEST_DRIVE_ONLY') THEN 1 ELSE 0 END AS promotion, "
                + "10 AS stockLevel, "
                + "LEAD(COUNT(o.id)) OVER(PARTITION BY o.dealer_id, o.car_model_id ORDER BY MONTH(o.created_on)) AS demandNextMonth "
                + "FROM dbo.orders o "
                + "JOIN dbo.car_model cm ON o.car_model_id = cm.id "
                + "LEFT JOIN dbo.program_detail pd ON o.car_model_id = pd.car_model_id "
                + "GROUP BY o.dealer_id, o.car_model_id, MONTH(o.created_on), pd.suggested_sale_price, cm.car_status "
                + "ORDER BY o.dealer_id, o.car_model_id, month;";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        FileWriter csv = new FileWriter("sales_data.csv");
        csv.append("dealerId,modelId,month,pastSales,price,promotion,stockLevel,demandNextMonth\n");

        while (rs.next()) {
            csv.append(rs.getInt("dealer_id") + ",")
                .append(rs.getInt("car_model_id") + ",")
                .append(rs.getInt("month") + ",")
                .append(rs.getInt("pastSales") + ",")
                .append(rs.getBigDecimal("price") + ",")
                .append(rs.getInt("promotion") + ",")
                .append(rs.getInt("stockLevel") + ",")
                .append(rs.getInt("demandNextMonth") + "\n");
        }

        csv.flush();
        csv.close();
        conn.close();
        System.out.println("sales_data.csv generated successfully!");
    }

}
