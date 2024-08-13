import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IUsuarioGestao } from '../usuario-gestao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../usuario-gestao.test-samples';

import { UsuarioGestaoService, RestUsuarioGestao } from './usuario-gestao.service';

const requireRestSample: RestUsuarioGestao = {
  ...sampleWithRequiredData,
  dataHoraAtivacao: sampleWithRequiredData.dataHoraAtivacao?.toJSON(),
  dataLimiteAcesso: sampleWithRequiredData.dataLimiteAcesso?.toJSON(),
};

describe('UsuarioGestao Service', () => {
  let service: UsuarioGestaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IUsuarioGestao | IUsuarioGestao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(UsuarioGestaoService);
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

    it('should create a UsuarioGestao', () => {
      const usuarioGestao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(usuarioGestao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UsuarioGestao', () => {
      const usuarioGestao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(usuarioGestao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UsuarioGestao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UsuarioGestao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UsuarioGestao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUsuarioGestaoToCollectionIfMissing', () => {
      it('should add a UsuarioGestao to an empty array', () => {
        const usuarioGestao: IUsuarioGestao = sampleWithRequiredData;
        expectedResult = service.addUsuarioGestaoToCollectionIfMissing([], usuarioGestao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(usuarioGestao);
      });

      it('should not add a UsuarioGestao to an array that contains it', () => {
        const usuarioGestao: IUsuarioGestao = sampleWithRequiredData;
        const usuarioGestaoCollection: IUsuarioGestao[] = [
          {
            ...usuarioGestao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUsuarioGestaoToCollectionIfMissing(usuarioGestaoCollection, usuarioGestao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UsuarioGestao to an array that doesn't contain it", () => {
        const usuarioGestao: IUsuarioGestao = sampleWithRequiredData;
        const usuarioGestaoCollection: IUsuarioGestao[] = [sampleWithPartialData];
        expectedResult = service.addUsuarioGestaoToCollectionIfMissing(usuarioGestaoCollection, usuarioGestao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(usuarioGestao);
      });

      it('should add only unique UsuarioGestao to an array', () => {
        const usuarioGestaoArray: IUsuarioGestao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const usuarioGestaoCollection: IUsuarioGestao[] = [sampleWithRequiredData];
        expectedResult = service.addUsuarioGestaoToCollectionIfMissing(usuarioGestaoCollection, ...usuarioGestaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const usuarioGestao: IUsuarioGestao = sampleWithRequiredData;
        const usuarioGestao2: IUsuarioGestao = sampleWithPartialData;
        expectedResult = service.addUsuarioGestaoToCollectionIfMissing([], usuarioGestao, usuarioGestao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(usuarioGestao);
        expect(expectedResult).toContain(usuarioGestao2);
      });

      it('should accept null and undefined values', () => {
        const usuarioGestao: IUsuarioGestao = sampleWithRequiredData;
        expectedResult = service.addUsuarioGestaoToCollectionIfMissing([], null, usuarioGestao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(usuarioGestao);
      });

      it('should return initial array if no UsuarioGestao is added', () => {
        const usuarioGestaoCollection: IUsuarioGestao[] = [sampleWithRequiredData];
        expectedResult = service.addUsuarioGestaoToCollectionIfMissing(usuarioGestaoCollection, undefined, null);
        expect(expectedResult).toEqual(usuarioGestaoCollection);
      });
    });

    describe('compareUsuarioGestao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUsuarioGestao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUsuarioGestao(entity1, entity2);
        const compareResult2 = service.compareUsuarioGestao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUsuarioGestao(entity1, entity2);
        const compareResult2 = service.compareUsuarioGestao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUsuarioGestao(entity1, entity2);
        const compareResult2 = service.compareUsuarioGestao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
