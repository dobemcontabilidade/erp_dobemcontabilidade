import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFornecedorCertificado } from '../fornecedor-certificado.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../fornecedor-certificado.test-samples';

import { FornecedorCertificadoService } from './fornecedor-certificado.service';

const requireRestSample: IFornecedorCertificado = {
  ...sampleWithRequiredData,
};

describe('FornecedorCertificado Service', () => {
  let service: FornecedorCertificadoService;
  let httpMock: HttpTestingController;
  let expectedResult: IFornecedorCertificado | IFornecedorCertificado[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FornecedorCertificadoService);
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

    it('should create a FornecedorCertificado', () => {
      const fornecedorCertificado = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fornecedorCertificado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FornecedorCertificado', () => {
      const fornecedorCertificado = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fornecedorCertificado).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FornecedorCertificado', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FornecedorCertificado', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FornecedorCertificado', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFornecedorCertificadoToCollectionIfMissing', () => {
      it('should add a FornecedorCertificado to an empty array', () => {
        const fornecedorCertificado: IFornecedorCertificado = sampleWithRequiredData;
        expectedResult = service.addFornecedorCertificadoToCollectionIfMissing([], fornecedorCertificado);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fornecedorCertificado);
      });

      it('should not add a FornecedorCertificado to an array that contains it', () => {
        const fornecedorCertificado: IFornecedorCertificado = sampleWithRequiredData;
        const fornecedorCertificadoCollection: IFornecedorCertificado[] = [
          {
            ...fornecedorCertificado,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFornecedorCertificadoToCollectionIfMissing(fornecedorCertificadoCollection, fornecedorCertificado);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FornecedorCertificado to an array that doesn't contain it", () => {
        const fornecedorCertificado: IFornecedorCertificado = sampleWithRequiredData;
        const fornecedorCertificadoCollection: IFornecedorCertificado[] = [sampleWithPartialData];
        expectedResult = service.addFornecedorCertificadoToCollectionIfMissing(fornecedorCertificadoCollection, fornecedorCertificado);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fornecedorCertificado);
      });

      it('should add only unique FornecedorCertificado to an array', () => {
        const fornecedorCertificadoArray: IFornecedorCertificado[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fornecedorCertificadoCollection: IFornecedorCertificado[] = [sampleWithRequiredData];
        expectedResult = service.addFornecedorCertificadoToCollectionIfMissing(
          fornecedorCertificadoCollection,
          ...fornecedorCertificadoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fornecedorCertificado: IFornecedorCertificado = sampleWithRequiredData;
        const fornecedorCertificado2: IFornecedorCertificado = sampleWithPartialData;
        expectedResult = service.addFornecedorCertificadoToCollectionIfMissing([], fornecedorCertificado, fornecedorCertificado2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fornecedorCertificado);
        expect(expectedResult).toContain(fornecedorCertificado2);
      });

      it('should accept null and undefined values', () => {
        const fornecedorCertificado: IFornecedorCertificado = sampleWithRequiredData;
        expectedResult = service.addFornecedorCertificadoToCollectionIfMissing([], null, fornecedorCertificado, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fornecedorCertificado);
      });

      it('should return initial array if no FornecedorCertificado is added', () => {
        const fornecedorCertificadoCollection: IFornecedorCertificado[] = [sampleWithRequiredData];
        expectedResult = service.addFornecedorCertificadoToCollectionIfMissing(fornecedorCertificadoCollection, undefined, null);
        expect(expectedResult).toEqual(fornecedorCertificadoCollection);
      });
    });

    describe('compareFornecedorCertificado', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFornecedorCertificado(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFornecedorCertificado(entity1, entity2);
        const compareResult2 = service.compareFornecedorCertificado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFornecedorCertificado(entity1, entity2);
        const compareResult2 = service.compareFornecedorCertificado(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFornecedorCertificado(entity1, entity2);
        const compareResult2 = service.compareFornecedorCertificado(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
