; PROGRAM : U3_5_3.ASM            		By Steven                    
; FUNCTION: MDU Demo Program   			2025.0625      		  
#INCLUDE    HT66F2390.INC 
;=================================================================================
MY_DATA    .SECTION			'DATA'      ;== DATA SECTION == 
index   DB  	? 
i    	DB  2 DUP(?)         
j    	DB  2 DUP(?)         
RAMBANK 3  	MY_DATA1					;﹚MY_DATA1RAM Section 3(與U3_5_2.C的Section2比較驗證)
MY_DATA1    .SECTION  'DATA'         	;== DATA SECTION == 
product	DB  128 DUP(?)        			;product[32]
;========================================================================================
MY_CODE    .SECTION	 at 0	'CODE'     	;== PROGRAM SECTION, 竚秖(RESET VECTOR)                	
MAIN: 
		MOV		A,10101111B				;闽超WDT
		MOV		WDTC,A
		MOV		A,HIGH 256				;i=256, j=256
		MOV		i[1],A
		MOV		j[1],A
		MOV		A,LOW  256			
		MOV		i[0],A
		MOV		j[0],A
		MOV		A,32					;for(...)		
		MOV		index,A					;index=32
		MOV		A,HIGH OFFSET product	;MPIH/L==>product 
		MOV		MP1H,A
		MOV		A,LOW  OFFSET product
		MOV		MP1L,A
FOR:	MOV		A,i[0]					;更计Low Byte
		MOV		MDUWR0,A
		MOV		A,j[0]					;更砆计Low Byte
		MOV		MDUWR4,A
		MOV		A,i[1]					;更计High Byte
		MOV		MDUWR1,A
		MOV		A,j[1]					;更砆计High Byte
		MOV		MDUWR5,A
WAIT:	MOV		A,MDUWR0				;;弄縩Bit[7:0]
		SZ		MDWEF					;MDWEF=1 ﹟ゼ笲衡ЧΘ	
		JMP		WAIT					;单
		MOV		IAR1,A
		INC		MP1L
		MOV		A,MDUWR1				;弄縩Bit[15:8]
		MOV		IAR1,A
		INC		MP1L
		MOV		A,MDUWR2				;弄縩Bit[23:16]
		MOV		IAR1,A
		INC		MP1L
		MOV		A,MDUWR3				;弄縩Bit[31:24]
		MOV		IAR1,A
		INC		MP1L

		MOV		A,100					;i+=100
		ADDM	A,i[0]	
		CLR		ACC
		ADCM	A,i[1]
			
		MOV		A,LOW  500				;j+=500
		ADDM	A,j[0]	
		MOV		A,HIGH 500
		ADCM	A,j[1]
		
		SDZ		index					;for(...)
		JMP		FOR						
		JMP		$									