print(".........1...........")
# String
for letter in "Hello World":
    print(letter)
# list
print(".........2...........")
fruits = ["apple", "banana", "cherry"]
for fruit in fruits:
    print(fruit)
# dictionary
print(".........3...........")
person = {"name": "Alice", "age": 30, "city": "New York"}
for key in person:
    print(key, ":", person[key])

# dictionary，取得key與value
print(".........4...........")
for key, value in person.items():
    print(key, ":", value)

# dict轉list
print(".........5...........")
list_data = []
for key, value in person.items():
    #print(key, ":", value)
    list_data.append(key)
    list_data.append(value)
print(list_data)

# list內有多個dict or (object)
print(".........6...........")
items = [
    {"name":"bill","score":90},
    {"name":"alice","score":85},
    {"name":"john","score":78}
]
for item in items:
    print(f"name: {item['name']}, score: {item['score']}")