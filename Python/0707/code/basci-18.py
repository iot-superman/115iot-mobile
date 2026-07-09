score = total = person = 0
while True:
    score = int(input("Enter score (or -1 to quit): "))
    if score == -1:
        break
    total += score # total = total + score
    person += 1 # person = person + 1
if person > 0:
    avg = total / person
    print(f"Total score: {total}")
    print(f"Average score: {avg:.2f}")
else:
    print("No scores were entered.")
