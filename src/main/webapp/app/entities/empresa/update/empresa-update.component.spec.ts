import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { PessoajuridicaService } from 'app/entities/pessoajuridica/service/pessoajuridica.service';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { TributacaoService } from 'app/entities/tributacao/service/tributacao.service';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { RamoService } from 'app/entities/ramo/service/ramo.service';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { EnquadramentoService } from 'app/entities/enquadramento/service/enquadramento.service';
import { IEmpresa } from '../empresa.model';
import { EmpresaService } from '../service/empresa.service';
import { EmpresaFormService } from './empresa-form.service';

import { EmpresaUpdateComponent } from './empresa-update.component';

describe('Empresa Management Update Component', () => {
  let comp: EmpresaUpdateComponent;
  let fixture: ComponentFixture<EmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let empresaFormService: EmpresaFormService;
  let empresaService: EmpresaService;
  let pessoajuridicaService: PessoajuridicaService;
  let tributacaoService: TributacaoService;
  let ramoService: RamoService;
  let enquadramentoService: EnquadramentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmpresaUpdateComponent],
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
      .overrideTemplate(EmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    empresaFormService = TestBed.inject(EmpresaFormService);
    empresaService = TestBed.inject(EmpresaService);
    pessoajuridicaService = TestBed.inject(PessoajuridicaService);
    tributacaoService = TestBed.inject(TributacaoService);
    ramoService = TestBed.inject(RamoService);
    enquadramentoService = TestBed.inject(EnquadramentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call pessoaJuridica query and add missing value', () => {
      const empresa: IEmpresa = { id: 456 };
      const pessoaJuridica: IPessoajuridica = { id: 31515 };
      empresa.pessoaJuridica = pessoaJuridica;

      const pessoaJuridicaCollection: IPessoajuridica[] = [{ id: 13568 }];
      jest.spyOn(pessoajuridicaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaJuridicaCollection })));
      const expectedCollection: IPessoajuridica[] = [pessoaJuridica, ...pessoaJuridicaCollection];
      jest.spyOn(pessoajuridicaService, 'addPessoajuridicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(pessoajuridicaService.query).toHaveBeenCalled();
      expect(pessoajuridicaService.addPessoajuridicaToCollectionIfMissing).toHaveBeenCalledWith(pessoaJuridicaCollection, pessoaJuridica);
      expect(comp.pessoaJuridicasCollection).toEqual(expectedCollection);
    });

    it('Should call Tributacao query and add missing value', () => {
      const empresa: IEmpresa = { id: 456 };
      const empresa: ITributacao = { id: 25043 };
      empresa.empresa = empresa;

      const tributacaoCollection: ITributacao[] = [{ id: 2282 }];
      jest.spyOn(tributacaoService, 'query').mockReturnValue(of(new HttpResponse({ body: tributacaoCollection })));
      const additionalTributacaos = [empresa];
      const expectedCollection: ITributacao[] = [...additionalTributacaos, ...tributacaoCollection];
      jest.spyOn(tributacaoService, 'addTributacaoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(tributacaoService.query).toHaveBeenCalled();
      expect(tributacaoService.addTributacaoToCollectionIfMissing).toHaveBeenCalledWith(
        tributacaoCollection,
        ...additionalTributacaos.map(expect.objectContaining),
      );
      expect(comp.tributacaosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Ramo query and add missing value', () => {
      const empresa: IEmpresa = { id: 456 };
      const ramo: IRamo = { id: 6913 };
      empresa.ramo = ramo;

      const ramoCollection: IRamo[] = [{ id: 16519 }];
      jest.spyOn(ramoService, 'query').mockReturnValue(of(new HttpResponse({ body: ramoCollection })));
      const additionalRamos = [ramo];
      const expectedCollection: IRamo[] = [...additionalRamos, ...ramoCollection];
      jest.spyOn(ramoService, 'addRamoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(ramoService.query).toHaveBeenCalled();
      expect(ramoService.addRamoToCollectionIfMissing).toHaveBeenCalledWith(
        ramoCollection,
        ...additionalRamos.map(expect.objectContaining),
      );
      expect(comp.ramosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Enquadramento query and add missing value', () => {
      const empresa: IEmpresa = { id: 456 };
      const enquadramento: IEnquadramento = { id: 30566 };
      empresa.enquadramento = enquadramento;

      const enquadramentoCollection: IEnquadramento[] = [{ id: 20873 }];
      jest.spyOn(enquadramentoService, 'query').mockReturnValue(of(new HttpResponse({ body: enquadramentoCollection })));
      const additionalEnquadramentos = [enquadramento];
      const expectedCollection: IEnquadramento[] = [...additionalEnquadramentos, ...enquadramentoCollection];
      jest.spyOn(enquadramentoService, 'addEnquadramentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(enquadramentoService.query).toHaveBeenCalled();
      expect(enquadramentoService.addEnquadramentoToCollectionIfMissing).toHaveBeenCalledWith(
        enquadramentoCollection,
        ...additionalEnquadramentos.map(expect.objectContaining),
      );
      expect(comp.enquadramentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const empresa: IEmpresa = { id: 456 };
      const pessoaJuridica: IPessoajuridica = { id: 26706 };
      empresa.pessoaJuridica = pessoaJuridica;
      const empresa: ITributacao = { id: 11703 };
      empresa.empresa = empresa;
      const ramo: IRamo = { id: 30601 };
      empresa.ramo = ramo;
      const enquadramento: IEnquadramento = { id: 12830 };
      empresa.enquadramento = enquadramento;

      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      expect(comp.pessoaJuridicasCollection).toContain(pessoaJuridica);
      expect(comp.tributacaosSharedCollection).toContain(empresa);
      expect(comp.ramosSharedCollection).toContain(ramo);
      expect(comp.enquadramentosSharedCollection).toContain(enquadramento);
      expect(comp.empresa).toEqual(empresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaFormService, 'getEmpresa').mockReturnValue(empresa);
      jest.spyOn(empresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresa }));
      saveSubject.complete();

      // THEN
      expect(empresaFormService.getEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(empresaService.update).toHaveBeenCalledWith(expect.objectContaining(empresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaFormService, 'getEmpresa').mockReturnValue({ id: null });
      jest.spyOn(empresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: empresa }));
      saveSubject.complete();

      // THEN
      expect(empresaFormService.getEmpresa).toHaveBeenCalled();
      expect(empresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmpresa>>();
      const empresa = { id: 123 };
      jest.spyOn(empresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ empresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(empresaService.update).toHaveBeenCalled();
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

    describe('compareTributacao', () => {
      it('Should forward to tributacaoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tributacaoService, 'compareTributacao');
        comp.compareTributacao(entity, entity2);
        expect(tributacaoService.compareTributacao).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareRamo', () => {
      it('Should forward to ramoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(ramoService, 'compareRamo');
        comp.compareRamo(entity, entity2);
        expect(ramoService.compareRamo).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEnquadramento', () => {
      it('Should forward to enquadramentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(enquadramentoService, 'compareEnquadramento');
        comp.compareEnquadramento(entity, entity2);
        expect(enquadramentoService.compareEnquadramento).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
