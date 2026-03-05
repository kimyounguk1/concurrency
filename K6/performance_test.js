import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    // 1. 설정: 100명이 동시에(vus) 총 200번(iterations) 실행
    vus: 100,
    iterations: 200,
};

export default function () {
    // 2. 요청 보낼 URL (영욱님의 컨트롤러 주소와 ID에 맞게 수정하세요)
    // 예: http://localhost:8080/distribution/update/1
    //     http://localhost:8080/optimistic/update/1
    //     http://localhost:8080/pessimistic/update/1
    //     http://localhost:8080/atomic/update/1
    const url = 'http://host.docker.internal:8080/atomic/update/1';
    // const payload = JSON.stringify({
    //     quantity: 1,
    // });

    // const params = {
    //     headers: {
    //         'Content-Type': 'application/json',
    //     },
    // };

    const res = http.patch(url);

    // 3. 결과 검증: 응답 코드가 200(성공)인지 확인
    check(res, {
        'is status 200': (r) => r.status === 200,
    });

    // 너무 순식간에 끝나지 않게 아주 잠깐의 휴식 (선택사항)
    sleep(0.1);
}