import fs from 'fs';
import axios from 'axios';
import dotenv from 'dotenv';
import { execSync } from 'child_process'

dotenv.config();
const secretKey = process.env.VITE_SECRET_KEY;
const mode = process.env.NODE_ENV === 'prod' ? 'prod' : 'dev';
const path = `.env.${mode}`;
const encodingFormat = 'utf8'
const encryptedEnvVariables = read();
const requestUrl = process.env.VITE_DECRYPTION_REQUEST_URL
const requestHeaders = process.env.VITE_DECRYPTION_REQUEST_HEADERS

function read() {
    return fs.readFileSync(path, encodingFormat)
        .split('\n')
        .reduce((variables, line) => {
            const [key, value] = line.split(/=(.+)/);
            if (key && value) {
                variables[key.trim()] = value.trim();
            }
            return variables;
        }, {});
}

const decrypt = async (encryptedValue) => {
    const body = {
        decrypt: true,
        inputString: encryptedValue,
        secretKey: secretKey
    };

    return await axios.post(requestUrl, body, requestHeaders)
        .then(response => {
            return response.data.outputString
        })
        .catch(error => {
            console.error('Error:', error);
        })
}

const load = async () => {
    const promises = Object.entries(encryptedEnvVariables).map(async ([key, encryptedValue]) => {
        const decryptedValue = await decrypt(encryptedValue);
        if (decryptedValue) {
            process.env[key] = decryptedValue;
        }
    });

    await Promise.all(promises);
}

const run = async () => {
    await load()
    console.log('Decryption complete.')

    // 현재 실행 중인 npm 스크립트를 확인하여 빌드 또는 개발 서버를 실행
    const isBuild = process.env.BUILD_MODE === 'true';
    if (isBuild) {
        console.log('========== Starting Build ==========') // Build 시작 로그
        execSync('vite build', { stdio: 'inherit' })
    } else {
        execSync('vite', { stdio: 'inherit' })
    }
}

run().catch((error) => {
    console.error('-- Error -- \n:', error)
    process.exit(1)
})

// load()
//     .then(async () => {
//         const npmScript = process.env.npm_lifecycle_event;
//         console.log(npmScript)
//         console.log('========== Starting Vite =========='); // Vite 시작 로그
//         const server = await createServer();
//         await server.listen();
//         const address = server.httpServer.address();
//         console.log(`Vite dev server is url => ${address.address}:${address.port}`);
//     }).catch((error) => {
//     console.error('-- Error -- \n:', error);
// });



