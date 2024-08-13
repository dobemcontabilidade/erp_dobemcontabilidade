import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPerfilContadorDepartamento } from '../perfil-contador-departamento.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../perfil-contador-departamento.test-samples';

import { PerfilContadorDepartamentoService } from './perfil-contador-departamento.service';

const requireRestSample: IPerfilContadorDepartamento = {
  ...sampleWithRequiredData,
};

describe('PerfilContadorDepartamento Service', () => {
  let service: PerfilContadorDepartamentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IPerfilContadorDepartamento | IPerfilContadorDepartamento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PerfilContadorDepartamentoService);
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

    it('should create a PerfilContadorDepartamento', () => {
      const perfilContadorDepartamento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(perfilContadorDepartamento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PerfilContadorDepartamento', () => {
      const perfilContadorDepartamento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(perfilContadorDepartamento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PerfilContadorDepartamento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PerfilContadorDepartamento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PerfilContadorDepartamento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPerfilContadorDepartamentoToCollectionIfMissing', () => {
      it('should add a PerfilContadorDepartamento to an empty array', () => {
        const perfilContadorDepartamento: IPerfilContadorDepartamento = sampleWithRequiredData;
        expectedResult = service.addPerfilContadorDepartamentoToCollectionIfMissing([], perfilContadorDepartamento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilContadorDepartamento);
      });

      it('should not add a PerfilContadorDepartamento to an array that contains it', () => {
        const perfilContadorDepartamento: IPerfilContadorDepartamento = sampleWithRequiredData;
        const perfilContadorDepartamentoCollection: IPerfilContadorDepartamento[] = [
          {
            ...perfilContadorDepartamento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPerfilContadorDepartamentoToCollectionIfMissing(
          perfilContadorDepartamentoCollection,
          perfilContadorDepartamento,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PerfilContadorDepartamento to an array that doesn't contain it", () => {
        const perfilContadorDepartamento: IPerfilContadorDepartamento = sampleWithRequiredData;
        const perfilContadorDepartamentoCollection: IPerfilContadorDepartamento[] = [sampleWithPartialData];
        expectedResult = service.addPerfilContadorDepartamentoToCollectionIfMissing(
          perfilContadorDepartamentoCollection,
          perfilContadorDepartamento,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilContadorDepartamento);
      });

      it('should add only unique PerfilContadorDepartamento to an array', () => {
        const perfilContadorDepartamentoArray: IPerfilContadorDepartamento[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const perfilContadorDepartamentoCollection: IPerfilContadorDepartamento[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilContadorDepartamentoToCollectionIfMissing(
          perfilContadorDepartamentoCollection,
          ...perfilContadorDepartamentoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const perfilContadorDepartamento: IPerfilContadorDepartamento = sampleWithRequiredData;
        const perfilContadorDepartamento2: IPerfilContadorDepartamento = sampleWithPartialData;
        expectedResult = service.addPerfilContadorDepartamentoToCollectionIfMissing(
          [],
          perfilContadorDepartamento,
          perfilContadorDepartamento2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilContadorDepartamento);
        expect(expectedResult).toContain(perfilContadorDepartamento2);
      });

      it('should accept null and undefined values', () => {
        const perfilContadorDepartamento: IPerfilContadorDepartamento = sampleWithRequiredData;
        expectedResult = service.addPerfilContadorDepartamentoToCollectionIfMissing([], null, perfilContadorDepartamento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilContadorDepartamento);
      });

      it('should return initial array if no PerfilContadorDepartamento is added', () => {
        const perfilContadorDepartamentoCollection: IPerfilContadorDepartamento[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilContadorDepartamentoToCollectionIfMissing(perfilContadorDepartamentoCollection, undefined, null);
        expect(expectedResult).toEqual(perfilContadorDepartamentoCollection);
      });
    });

    describe('comparePerfilContadorDepartamento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePerfilContadorDepartamento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePerfilContadorDepartamento(entity1, entity2);
        const compareResult2 = service.comparePerfilContadorDepartamento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePerfilContadorDepartamento(entity1, entity2);
        const compareResult2 = service.comparePerfilContadorDepartamento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePerfilContadorDepartamento(entity1, entity2);
        const compareResult2 = service.comparePerfilContadorDepartamento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
