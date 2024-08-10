import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISocio } from '../socio.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../socio.test-samples';

import { SocioService } from './socio.service';

const requireRestSample: ISocio = {
  ...sampleWithRequiredData,
};

describe('Socio Service', () => {
  let service: SocioService;
  let httpMock: HttpTestingController;
  let expectedResult: ISocio | ISocio[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SocioService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Socio', () => {
      const socio = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(socio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Socio', () => {
      const socio = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(socio).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Socio', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Socio', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Socio', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSocioToCollectionIfMissing', () => {
      it('should add a Socio to an empty array', () => {
        const socio: ISocio = sampleWithRequiredData;
        expectedResult = service.addSocioToCollectionIfMissing([], socio);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(socio);
      });

      it('should not add a Socio to an array that contains it', () => {
        const socio: ISocio = sampleWithRequiredData;
        const socioCollection: ISocio[] = [
          {
            ...socio,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSocioToCollectionIfMissing(socioCollection, socio);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Socio to an array that doesn't contain it", () => {
        const socio: ISocio = sampleWithRequiredData;
        const socioCollection: ISocio[] = [sampleWithPartialData];
        expectedResult = service.addSocioToCollectionIfMissing(socioCollection, socio);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(socio);
      });

      it('should add only unique Socio to an array', () => {
        const socioArray: ISocio[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const socioCollection: ISocio[] = [sampleWithRequiredData];
        expectedResult = service.addSocioToCollectionIfMissing(socioCollection, ...socioArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const socio: ISocio = sampleWithRequiredData;
        const socio2: ISocio = sampleWithPartialData;
        expectedResult = service.addSocioToCollectionIfMissing([], socio, socio2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(socio);
        expect(expectedResult).toContain(socio2);
      });

      it('should accept null and undefined values', () => {
        const socio: ISocio = sampleWithRequiredData;
        expectedResult = service.addSocioToCollectionIfMissing([], null, socio, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(socio);
      });

      it('should return initial array if no Socio is added', () => {
        const socioCollection: ISocio[] = [sampleWithRequiredData];
        expectedResult = service.addSocioToCollectionIfMissing(socioCollection, undefined, null);
        expect(expectedResult).toEqual(socioCollection);
      });
    });

    describe('compareSocio', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSocio(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSocio(entity1, entity2);
        const compareResult2 = service.compareSocio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSocio(entity1, entity2);
        const compareResult2 = service.compareSocio(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSocio(entity1, entity2);
        const compareResult2 = service.compareSocio(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
