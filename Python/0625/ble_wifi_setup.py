import asyncio
from bleak import BleakScanner, BleakClient

# ==================================================
# 修改這裡
# ==================================================

DEVICE_NAME = "ESP32S3_SCALE"

RX_UUID = "6E400002-B5A3-F393-E0A9-E50E24DCCA9E"

SSID = "thmrb306"

PASSWORD = "thmrbthmrb"

# ==================================================

async def main():

    print("Scanning BLE...")

    device = await BleakScanner.find_device_by_name(
        DEVICE_NAME,
        timeout=10.0
    )

    if device is None:
        print("找不到裝置：", DEVICE_NAME)
        return

    print("===================================")
    print("Found Device")
    print("Name :", device.name)
    print("MAC  :", device.address)
    print("===================================")

    async with BleakClient(device) as client:

        print("Connected :", client.is_connected)

        text = f"{SSID}:{PASSWORD}"

        print("Send ->", text)

        await client.write_gatt_char(
            RX_UUID,
            text.encode("utf-8"),
            response=True
        )

        print("===================================")
        print("WiFi Setting Sent Successfully")
        print("===================================")

asyncio.run(main())