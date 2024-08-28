import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { PessoajuridicaService } from 'app/entities/pessoajuridica/service/pessoajuridica.service';
import { ICertificadoDigital } from 'app/entities/certificado-digital/certificado-digital.model';
import { CertificadoDigitalService } from 'app/entities/certificado-digital/service/certificado-digital.service';
import { IFornecedorCertificado } from 'app/entities/fornecedor-certificado/fornecedor-certificado.model';
import { FornecedorCertificadoService } from 'app/entities/fornecedor-certificado/service/fornecedor-certificado.service';
import { ICertificadoDigitalEmpresa } from '../certificado-digital-empresa.model';
import { CertificadoDigitalEmpresaService } from '../service/certificado-digital-empresa.service';
import { CertificadoDigitalEmpresaFormService } from './certificado-digital-empresa-form.service';

import { CertificadoDigitalEmpresaUpdateComponent } from './certificado-digital-empresa-update.component';

describe('CertificadoDigitalEmpresa Management Update Component', () => {
  let comp: CertificadoDigitalEmpresaUpdateComponent;
  let fixture: ComponentFixture<CertificadoDigitalEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let certificadoDigitalEmpresaFormService: CertificadoDigitalEmpresaFormService;
  let certificadoDigitalEmpresaService: CertificadoDigitalEmpresaService;
  let pessoajuridicaService: PessoajuridicaService;
  let certificadoDigitalService: CertificadoDigitalService;
  let fornecedorCertificadoService: FornecedorCertificadoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CertificadoDigitalEmpresaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CertificadoDigitalEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CertificadoDigitalEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    certificadoDigitalEmpresaFormService = TestBed.inject(CertificadoDigitalEmpresaFormService);
    certificadoDigitalEmpresaService = TestBed.inject(CertificadoDigitalEmpresaService);
    pessoajuridicaService = TestBed.inject(PessoajuridicaService);
    certificadoDigitalService = TestBed.inject(CertificadoDigitalService);
    fornecedorCertificadoService = TestBed.inject(FornecedorCertificadoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Pessoajuridica query and add missing value', () => {
      const certificadoDigitalEmpresa: ICertificadoDigitalEmpresa = { id: 456 };
      const pessoaJuridica: IPessoajuridica = { id: 13146 };
      certificadoDigitalEmpresa.pessoaJuridica = pessoaJuridica;

      const pessoajuridicaCollection: IPessoajuridica[] = [{ id: 21208 }];
      jest.spyOn(pessoajuridicaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoajuridicaCollection })));
      const additionalPessoajuridicas = [pessoaJuridica];
      const expectedCollection: IPessoajuridica[] = [...additionalPessoajuridicas, ...pessoajuridicaCollection];
      jest.spyOn(pessoajuridicaService, 'addPessoajuridicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ certificadoDigitalEmpresa });
      comp.ngOnInit();

      expect(pessoajuridicaService.query).toHaveBeenCalled();
      expect(pessoajuridicaService.addPessoajuridicaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoajuridicaCollection,
        ...additionalPessoajuridicas.map(expect.objectContaining),
      );
      expect(comp.pessoajuridicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call CertificadoDigital query and add missing value', () => {
      const certificadoDigitalEmpresa: ICertificadoDigitalEmpresa = { id: 456 };
      const certificadoDigital: ICertificadoDigital = { id: 19922 };
      certificadoDigitalEmpresa.certificadoDigital = certificadoDigital;

      const certificadoDigitalCollection: ICertificadoDigital[] = [{ id: 32407 }];
      jest.spyOn(certificadoDigitalService, 'query').mockReturnValue(of(new HttpResponse({ body: certificadoDigitalCollection })));
      const additionalCertificadoDigitals = [certificadoDigital];
      const expectedCollection: ICertificadoDigital[] = [...additionalCertificadoDigitals, ...certificadoDigitalCollection];
      jest.spyOn(certificadoDigitalService, 'addCertificadoDigitalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ certificadoDigitalEmpresa });
      comp.ngOnInit();

      expect(certificadoDigitalService.query).toHaveBeenCalled();
      expect(certificadoDigitalService.addCertificadoDigitalToCollectionIfMissing).toHaveBeenCalledWith(
        certificadoDigitalCollection,
        ...additionalCertificadoDigitals.map(expect.objectContaining),
      );
      expect(comp.certificadoDigitalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FornecedorCertificado query and add missing value', () => {
      const certificadoDigitalEmpresa: ICertificadoDigitalEmpresa = { id: 456 };
      const fornecedorCertificado: IFornecedorCertificado = { id: 2279 };
      certificadoDigitalEmpresa.fornecedorCertificado = fornecedorCertificado;

      const fornecedorCertificadoCollection: IFornecedorCertificado[] = [{ id: 24232 }];
      jest.spyOn(fornecedorCertificadoService, 'query').mockReturnValue(of(new HttpResponse({ body: fornecedorCertificadoCollection })));
      const additionalFornecedorCertificados = [fornecedorCertificado];
      const expectedCollection: IFornecedorCertificado[] = [...additionalFornecedorCertificados, ...fornecedorCertificadoCollection];
      jest.spyOn(fornecedorCertificadoService, 'addFornecedorCertificadoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ certificadoDigitalEmpresa });
      comp.ngOnInit();

      expect(fornecedorCertificadoService.query).toHaveBeenCalled();
      expect(fornecedorCertificadoService.addFornecedorCertificadoToCollectionIfMissing).toHaveBeenCalledWith(
        fornecedorCertificadoCollection,
        ...additionalFornecedorCertificados.map(expect.objectContaining),
      );
      expect(comp.fornecedorCertificadosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const certificadoDigitalEmpresa: ICertificadoDigitalEmpresa = { id: 456 };
      const pessoaJuridica: IPessoajuridica = { id: 21633 };
      certificadoDigitalEmpresa.pessoaJuridica = pessoaJuridica;
      const certificadoDigital: ICertificadoDigital = { id: 25159 };
      certificadoDigitalEmpresa.certificadoDigital = certificadoDigital;
      const fornecedorCertificado: IFornecedorCertificado = { id: 3667 };
      certificadoDigitalEmpresa.fornecedorCertificado = fornecedorCertificado;

      activatedRoute.data = of({ certificadoDigitalEmpresa });
      comp.ngOnInit();

      expect(comp.pessoajuridicasSharedCollection).toContain(pessoaJuridica);
      expect(comp.certificadoDigitalsSharedCollection).toContain(certificadoDigital);
      expect(comp.fornecedorCertificadosSharedCollection).toContain(fornecedorCertificado);
      expect(comp.certificadoDigitalEmpresa).toEqual(certificadoDigitalEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICertificadoDigitalEmpresa>>();
      const certificadoDigitalEmpresa = { id: 123 };
      jest.spyOn(certificadoDigitalEmpresaFormService, 'getCertificadoDigitalEmpresa').mockReturnValue(certificadoDigitalEmpresa);
      jest.spyOn(certificadoDigitalEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ certificadoDigitalEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: certificadoDigitalEmpresa }));
      saveSubject.complete();

      // THEN
      expect(certificadoDigitalEmpresaFormService.getCertificadoDigitalEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(certificadoDigitalEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(certificadoDigitalEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICertificadoDigitalEmpresa>>();
      const certificadoDigitalEmpresa = { id: 123 };
      jest.spyOn(certificadoDigitalEmpresaFormService, 'getCertificadoDigitalEmpresa').mockReturnValue({ id: null });
      jest.spyOn(certificadoDigitalEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ certificadoDigitalEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: certificadoDigitalEmpresa }));
      saveSubject.complete();

      // THEN
      expect(certificadoDigitalEmpresaFormService.getCertificadoDigitalEmpresa).toHaveBeenCalled();
      expect(certificadoDigitalEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICertificadoDigitalEmpresa>>();
      const certificadoDigitalEmpresa = { id: 123 };
      jest.spyOn(certificadoDigitalEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ certificadoDigitalEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(certificadoDigitalEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoajuridica', () => {
      it('Should forward to pessoajuridicaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoajuridicaService, 'comparePessoajuridica');
        comp.comparePessoajuridica(entity, entity2);
        expect(pessoajuridicaService.comparePessoajuridica).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCertificadoDigital', () => {
      it('Should forward to certificadoDigitalService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(certificadoDigitalService, 'compareCertificadoDigital');
        comp.compareCertificadoDigital(entity, entity2);
        expect(certificadoDigitalService.compareCertificadoDigital).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFornecedorCertificado', () => {
      it('Should forward to fornecedorCertificadoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(fornecedorCertificadoService, 'compareFornecedorCertificado');
        comp.compareFornecedorCertificado(entity, entity2);
        expect(fornecedorCertificadoService.compareFornecedorCertificado).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
