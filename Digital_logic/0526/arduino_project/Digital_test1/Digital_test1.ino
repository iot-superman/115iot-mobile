 

void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  Serial.println("test 1");

  byte value;
  value=57;
  Serial.print("57 in Bin=");
  Serial.println(value,BIN);
  Serial.print("57 in Hex=");
  Serial.println(value,HEX);
  Serial.println(1.23456,2);
  Serial.println();

  byte led_control=B00000000;
  led_control = led_control |(1<<7);
  Serial.print("led control =");
  Serial.println(led_control,BIN);

  led_control=B11111111;

  Serial.print("led control =");
  Serial.println(led_control,BIN);

  Serial.println(" 11<<4");
  value = 1<<4;
  Serial.print("value =");
  Serial.println(value,BIN);
  value=~value;
  Serial.print("~value =");
  Serial.println(value,BIN);

   led_control&=value;
  Serial.print("led control =");
  Serial.println(led_control,BIN);

   
  led_control&- !(1<<1);
  Serial.print("led control =");
  Serial.println(led_control,BIN);
  
  int x,y;
  x=-1;
  
 Serial.print("y =");
 Serial.println(y,BIN);
 Serial.print("y =");
 Serial.println(y,HEX);

  y=x+40000;
  
 Serial.print("y =");
 Serial.println(y,BIN);
 Serial.print("y =");
 Serial.println(y,HEX);
 
  y=x-40000;
  
 Serial.print("y =");
 Serial.println(y,BIN);
 Serial.print("y =");
 Serial.println(y,HEX);
}

void loop() {
  // put your main code here, to run repeatedly:

}
