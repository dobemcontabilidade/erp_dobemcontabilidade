import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IUsuarioContador } from '../usuario-contador.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../usuario-contador.test-samples';

import { UsuarioContadorService, RestUsuarioContador } from './usuario-contador.service';

const requireRestSample: RestUsuarioContador = {
  ...sampleWithRequiredData,
  dataHoraAtivacao: sampleWithRequiredData.dataHoraAtivacao?.toJSON(),
  dataLimiteAcesso: sampleWithRequiredData.dataLimiteAcesso?.toJSON(),
};

describe('UsuarioContador Service', () => {
  let service: UsuarioContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IUsuarioContador | IUsuarioContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(UsuarioContadorService);
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

    it('should create a UsuarioContador', () => {
      const usuarioContador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(usuarioContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UsuarioContador', () => {
      const usuarioContador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(usuarioContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UsuarioContador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UsuarioContador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UsuarioContador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUsuarioContadorToCollectionIfMissing', () => {
      it('should add a UsuarioContador to an empty array', () => {
        const usuarioContador: IUsuarioContador = sampleWithRequiredData;
        expectedResult = service.addUsuarioContadorToCollectionIfMissing([], usuarioContador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(usuarioContador);
      });

      it('should not add a UsuarioContador to an array that contains it', () => {
        const usuarioContador: IUsuarioContador = sampleWithRequiredData;
        const usuarioContadorCollection: IUsuarioContador[] = [
          {
            ...usuarioContador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUsuarioContadorToCollectionIfMissing(usuarioContadorCollection, usuarioContador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UsuarioContador to an array that doesn't contain it", () => {
        const usuarioContador: IUsuarioContador = sampleWithRequiredData;
        const usuarioContadorCollection: IUsuarioContador[] = [sampleWithPartialData];
        expectedResult = service.addUsuarioContadorToCollectionIfMissing(usuarioContadorCollection, usuarioContador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(usuarioContador);
      });

      it('should add only unique UsuarioContador to an array', () => {
        const usuarioContadorArray: IUsuarioContador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const usuarioContadorCollection: IUsuarioContador[] = [sampleWithRequiredData];
        expectedResult = service.addUsuarioContadorToCollectionIfMissing(usuarioContadorCollection, ...usuarioContadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const usuarioContador: IUsuarioContador = sampleWithRequiredData;
        const usuarioContador2: IUsuarioContador = sampleWithPartialData;
        expectedResult = service.addUsuarioContadorToCollectionIfMissing([], usuarioContador, usuarioContador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(usuarioContador);
        expect(expectedResult).toContain(usuarioContador2);
      });

      it('should accept null and undefined values', () => {
        const usuarioContador: IUsuarioContador = sampleWithRequiredData;
        expectedResult = service.addUsuarioContadorToCollectionIfMissing([], null, usuarioContador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(usuarioContador);
      });

      it('should return initial array if no UsuarioContador is added', () => {
        const usuarioContadorCollection: IUsuarioContador[] = [sampleWithRequiredData];
        expectedResult = service.addUsuarioContadorToCollectionIfMissing(usuarioContadorCollection, undefined, null);
        expect(expectedResult).toEqual(usuarioContadorCollection);
      });
    });

    describe('compareUsuarioContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUsuarioContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUsuarioContador(entity1, entity2);
        const compareResult2 = service.compareUsuarioContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUsuarioContador(entity1, entity2);
        const compareResult2 = service.compareUsuarioContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUsuarioContador(entity1, entity2);
        const compareResult2 = service.compareUsuarioContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
