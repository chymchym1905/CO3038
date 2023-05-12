import time
iscalled = 0

def my_function():
    iscalled = 1
    if my_function():
        iscalled = 0
    # Your function code here
    pass

start_time = time.time()
count = 0

while True:
    # Check if 2 seconds have passed
    if time.time() - start_time >= 2:
        break

    # Call function and increment count
    my_function()
    count += 1

# Print the number of times the function was called
print("Function called", count, "times in 2 seconds")
