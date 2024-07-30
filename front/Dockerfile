FROM node:16 AS build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM node:16 AS production-stage
WORKDIR /app
COPY --from=build-stage /app/dist ./dist
RUN npm install -g serve
CMD ["serve", "-s", "dist"]
EXPOSE 3000