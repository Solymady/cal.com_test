FROM maven:3.8.6-openjdk-11

# Set working directory inside the container
WORKDIR /app

# Copy entire project into the container
COPY . .

# Install Chrome & ChromeDriver (needed for Selenium)
RUN apt-get update && apt-get install -y wget unzip curl \
    && curl -fsSL https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list \
    && apt-get update && apt-get install -y google-chrome-stable \
    && wget -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/114.0.5735.90/chromedriver_linux64.zip \
    && unzip /tmp/chromedriver.zip -d /usr/bin/ \
    && rm /tmp/chromedriver.zip

# Run Selenium tests
CMD ["mvn", "test"]
