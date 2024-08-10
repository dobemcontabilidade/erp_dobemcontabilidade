import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPerfilContador } from '../perfil-contador.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../perfil-contador.test-samples';

import { PerfilContadorService } from './perfil-contador.service';

const requireRestSample: IPerfilContador = {
  ...sampleWithRequiredData,
};

describe('PerfilContador Service', () => {
  let service: PerfilContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IPerfilContador | IPerfilContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PerfilContadorService);
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

    it('should create a PerfilContador', () => {
      const perfilContador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(perfilContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PerfilContador', () => {
      const perfilContador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(perfilContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PerfilContador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PerfilContador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PerfilContador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPerfilContadorToCollectionIfMissing', () => {
      it('should add a PerfilContador to an empty array', () => {
        const perfilContador: IPerfilContador = sampleWithRequiredData;
        expectedResult = service.addPerfilContadorToCollectionIfMissing([], perfilContador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilContador);
      });

      it('should not add a PerfilContador to an array that contains it', () => {
        const perfilContador: IPerfilContador = sampleWithRequiredData;
        const perfilContadorCollection: IPerfilContador[] = [
          {
            ...perfilContador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPerfilContadorToCollectionIfMissing(perfilContadorCollection, perfilContador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PerfilContador to an array that doesn't contain it", () => {
        const perfilContador: IPerfilContador = sampleWithRequiredData;
        const perfilContadorCollection: IPerfilContador[] = [sampleWithPartialData];
        expectedResult = service.addPerfilContadorToCollectionIfMissing(perfilContadorCollection, perfilContador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilContador);
      });

      it('should add only unique PerfilContador to an array', () => {
        const perfilContadorArray: IPerfilContador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const perfilContadorCollection: IPerfilContador[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilContadorToCollectionIfMissing(perfilContadorCollection, ...perfilContadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const perfilContador: IPerfilContador = sampleWithRequiredData;
        const perfilContador2: IPerfilContador = sampleWithPartialData;
        expectedResult = service.addPerfilContadorToCollectionIfMissing([], perfilContador, perfilContador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilContador);
        expect(expectedResult).toContain(perfilContador2);
      });

      it('should accept null and undefined values', () => {
        const perfilContador: IPerfilContador = sampleWithRequiredData;
        expectedResult = service.addPerfilContadorToCollectionIfMissing([], null, perfilContador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilContador);
      });

      it('should return initial array if no PerfilContador is added', () => {
        const perfilContadorCollection: IPerfilContador[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilContadorToCollectionIfMissing(perfilContadorCollection, undefined, null);
        expect(expectedResult).toEqual(perfilContadorCollection);
      });
    });

    describe('comparePerfilContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePerfilContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePerfilContador(entity1, entity2);
        const compareResult2 = service.comparePerfilContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePerfilContador(entity1, entity2);
        const compareResult2 = service.comparePerfilContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePerfilContador(entity1, entity2);
        const compareResult2 = service.comparePerfilContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
