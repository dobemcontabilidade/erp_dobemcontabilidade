import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAreaContabilContador } from '../area-contabil-contador.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../area-contabil-contador.test-samples';

import { AreaContabilContadorService } from './area-contabil-contador.service';

const requireRestSample: IAreaContabilContador = {
  ...sampleWithRequiredData,
};

describe('AreaContabilContador Service', () => {
  let service: AreaContabilContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IAreaContabilContador | IAreaContabilContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AreaContabilContadorService);
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

    it('should create a AreaContabilContador', () => {
      const areaContabilContador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(areaContabilContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AreaContabilContador', () => {
      const areaContabilContador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(areaContabilContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AreaContabilContador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AreaContabilContador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AreaContabilContador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAreaContabilContadorToCollectionIfMissing', () => {
      it('should add a AreaContabilContador to an empty array', () => {
        const areaContabilContador: IAreaContabilContador = sampleWithRequiredData;
        expectedResult = service.addAreaContabilContadorToCollectionIfMissing([], areaContabilContador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaContabilContador);
      });

      it('should not add a AreaContabilContador to an array that contains it', () => {
        const areaContabilContador: IAreaContabilContador = sampleWithRequiredData;
        const areaContabilContadorCollection: IAreaContabilContador[] = [
          {
            ...areaContabilContador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAreaContabilContadorToCollectionIfMissing(areaContabilContadorCollection, areaContabilContador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AreaContabilContador to an array that doesn't contain it", () => {
        const areaContabilContador: IAreaContabilContador = sampleWithRequiredData;
        const areaContabilContadorCollection: IAreaContabilContador[] = [sampleWithPartialData];
        expectedResult = service.addAreaContabilContadorToCollectionIfMissing(areaContabilContadorCollection, areaContabilContador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaContabilContador);
      });

      it('should add only unique AreaContabilContador to an array', () => {
        const areaContabilContadorArray: IAreaContabilContador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const areaContabilContadorCollection: IAreaContabilContador[] = [sampleWithRequiredData];
        expectedResult = service.addAreaContabilContadorToCollectionIfMissing(areaContabilContadorCollection, ...areaContabilContadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const areaContabilContador: IAreaContabilContador = sampleWithRequiredData;
        const areaContabilContador2: IAreaContabilContador = sampleWithPartialData;
        expectedResult = service.addAreaContabilContadorToCollectionIfMissing([], areaContabilContador, areaContabilContador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaContabilContador);
        expect(expectedResult).toContain(areaContabilContador2);
      });

      it('should accept null and undefined values', () => {
        const areaContabilContador: IAreaContabilContador = sampleWithRequiredData;
        expectedResult = service.addAreaContabilContadorToCollectionIfMissing([], null, areaContabilContador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaContabilContador);
      });

      it('should return initial array if no AreaContabilContador is added', () => {
        const areaContabilContadorCollection: IAreaContabilContador[] = [sampleWithRequiredData];
        expectedResult = service.addAreaContabilContadorToCollectionIfMissing(areaContabilContadorCollection, undefined, null);
        expect(expectedResult).toEqual(areaContabilContadorCollection);
      });
    });

    describe('compareAreaContabilContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAreaContabilContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAreaContabilContador(entity1, entity2);
        const compareResult2 = service.compareAreaContabilContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAreaContabilContador(entity1, entity2);
        const compareResult2 = service.compareAreaContabilContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAreaContabilContador(entity1, entity2);
        const compareResult2 = service.compareAreaContabilContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
