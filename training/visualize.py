import sys
import json
import matplotlib.pyplot as plt

def plot_array_from_json(json_file):
    # Load data from JSON file
    with open(json_file, 'r') as file:
        data = json.load(file)

    # Extract array from loaded data
    array = data

    # Plot array content
    plt.plot(array)

    # Set labels and title
    plt.xlabel('Epoch')
    plt.ylabel('Days survived')
    plt.title('Days survived during training')

    # Show the plot
    plt.show()

if __name__ == "__main__":
    # Check if a filename is provided as a command-line argument
    if len(sys.argv) != 2:
        print("Usage: python script_name.py <json_file>")
        sys.exit(1)

    json_file_path = sys.argv[1]

    try:
        plot_array_from_json(json_file_path)
    except FileNotFoundError:
        print(f"Error: File '{json_file_path}' not found.")
    except json.JSONDecodeError:
        print(f"Error: Invalid JSON format in '{json_file_path}'.")
    except ValueError as ve:
        print(f"Error: {ve}")
