<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        
        <html> 
            <head>
                <style>
                    table, th, td {
                    border: 1px solid black;
                    border-collapse: collapse;
                    }
                </style>
            </head>
            
            <body>
                <h2>My CD Collection</h2>
                <table border="1">
                    <thead>
                        <tr bgcolor="#9acd32">
                            <th style="text-align:left">Title</th>
                            <th style="text-align:left">Artist</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="catalog/cd">
                            <tr>
                                <td>
                                    <xsl:value-of select="title"/>
                                </td>
                                <td>
                                    <xsl:value-of select="artist"/>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
