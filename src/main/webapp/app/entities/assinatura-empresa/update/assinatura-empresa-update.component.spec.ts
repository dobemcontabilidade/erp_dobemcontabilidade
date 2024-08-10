import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { PeriodoPagamentoService } from 'app/entities/periodo-pagamento/service/periodo-pagamento.service';
import { IFormaDePagamento } from 'app/entities/forma-de-pagamento/forma-de-pagamento.model';
import { FormaDePagamentoService } from 'app/entities/forma-de-pagamento/service/forma-de-pagamento.service';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { PlanoContabilService } from 'app/entities/plano-contabil/service/plano-contabil.service';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { IAssinaturaEmpresa } from '../assinatura-empresa.model';
import { AssinaturaEmpresaService } from '../service/assinatura-empresa.service';
import { AssinaturaEmpresaFormService } from './assinatura-empresa-form.service';

import { AssinaturaEmpresaUpdateComponent } from './assinatura-empresa-update.component';

describe('AssinaturaEmpresa Management Update Component', () => {
  let comp: AssinaturaEmpresaUpdateComponent;
  let fixture: ComponentFixture<AssinaturaEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assinaturaEmpresaFormService: AssinaturaEmpresaFormService;
  let assinaturaEmpresaService: AssinaturaEmpresaService;
  let periodoPagamentoService: PeriodoPagamentoService;
  let formaDePagamentoService: FormaDePagamentoService;
  let planoContabilService: PlanoContabilService;
  let empresaService: EmpresaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AssinaturaEmpresaUpdateComponent],
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
      .overrideTemplate(AssinaturaEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssinaturaEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assinaturaEmpresaFormService = TestBed.inject(AssinaturaEmpresaFormService);
    assinaturaEmpresaService = TestBed.inject(AssinaturaEmpresaService);
    periodoPagamentoService = TestBed.inject(PeriodoPagamentoService);
    formaDePagamentoService = TestBed.inject(FormaDePagamentoService);
    planoContabilService = TestBed.inject(PlanoContabilService);
    empresaService = TestBed.inject(EmpresaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call PeriodoPagamento query and add missing value', () => {
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 456 };
      const periodoPagamento: IPeriodoPagamento = { id: 8429 };
      assinaturaEmpresa.periodoPagamento = periodoPagamento;

      const periodoPagamentoCollection: IPeriodoPagamento[] = [{ id: 14523 }];
      jest.spyOn(periodoPagamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: periodoPagamentoCollection })));
      const additionalPeriodoPagamentos = [periodoPagamento];
      const expectedCollection: IPeriodoPagamento[] = [...additionalPeriodoPagamentos, ...periodoPagamentoCollection];
      jest.spyOn(periodoPagamentoService, 'addPeriodoPagamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assinaturaEmpresa });
      comp.ngOnInit();

      expect(periodoPagamentoService.query).toHaveBeenCalled();
      expect(periodoPagamentoService.addPeriodoPagamentoToCollectionIfMissing).toHaveBeenCalledWith(
        periodoPagamentoCollection,
        ...additionalPeriodoPagamentos.map(expect.objectContaining),
      );
      expect(comp.periodoPagamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FormaDePagamento query and add missing value', () => {
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 456 };
      const formaDePagamento: IFormaDePagamento = { id: 15144 };
      assinaturaEmpresa.formaDePagamento = formaDePagamento;

      const formaDePagamentoCollection: IFormaDePagamento[] = [{ id: 28885 }];
      jest.spyOn(formaDePagamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: formaDePagamentoCollection })));
      const additionalFormaDePagamentos = [formaDePagamento];
      const expectedCollection: IFormaDePagamento[] = [...additionalFormaDePagamentos, ...formaDePagamentoCollection];
      jest.spyOn(formaDePagamentoService, 'addFormaDePagamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assinaturaEmpresa });
      comp.ngOnInit();

      expect(formaDePagamentoService.query).toHaveBeenCalled();
      expect(formaDePagamentoService.addFormaDePagamentoToCollectionIfMissing).toHaveBeenCalledWith(
        formaDePagamentoCollection,
        ...additionalFormaDePagamentos.map(expect.objectContaining),
      );
      expect(comp.formaDePagamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should call PlanoContabil query and add missing value', () => {
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 456 };
      const planoContabil: IPlanoContabil = { id: 15945 };
      assinaturaEmpresa.planoContabil = planoContabil;

      const planoContabilCollection: IPlanoContabil[] = [{ id: 19468 }];
      jest.spyOn(planoContabilService, 'query').mockReturnValue(of(new HttpResponse({ body: planoContabilCollection })));
      const additionalPlanoContabils = [planoContabil];
      const expectedCollection: IPlanoContabil[] = [...additionalPlanoContabils, ...planoContabilCollection];
      jest.spyOn(planoContabilService, 'addPlanoContabilToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assinaturaEmpresa });
      comp.ngOnInit();

      expect(planoContabilService.query).toHaveBeenCalled();
      expect(planoContabilService.addPlanoContabilToCollectionIfMissing).toHaveBeenCalledWith(
        planoContabilCollection,
        ...additionalPlanoContabils.map(expect.objectContaining),
      );
      expect(comp.planoContabilsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Empresa query and add missing value', () => {
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 32451 };
      assinaturaEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 5636 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ assinaturaEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 456 };
      const periodoPagamento: IPeriodoPagamento = { id: 20291 };
      assinaturaEmpresa.periodoPagamento = periodoPagamento;
      const formaDePagamento: IFormaDePagamento = { id: 197 };
      assinaturaEmpresa.formaDePagamento = formaDePagamento;
      const planoContabil: IPlanoContabil = { id: 6906 };
      assinaturaEmpresa.planoContabil = planoContabil;
      const empresa: IEmpresa = { id: 1364 };
      assinaturaEmpresa.empresa = empresa;

      activatedRoute.data = of({ assinaturaEmpresa });
      comp.ngOnInit();

      expect(comp.periodoPagamentosSharedCollection).toContain(periodoPagamento);
      expect(comp.formaDePagamentosSharedCollection).toContain(formaDePagamento);
      expect(comp.planoContabilsSharedCollection).toContain(planoContabil);
      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.assinaturaEmpresa).toEqual(assinaturaEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssinaturaEmpresa>>();
      const assinaturaEmpresa = { id: 123 };
      jest.spyOn(assinaturaEmpresaFormService, 'getAssinaturaEmpresa').mockReturnValue(assinaturaEmpresa);
      jest.spyOn(assinaturaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assinaturaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assinaturaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(assinaturaEmpresaFormService.getAssinaturaEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(assinaturaEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(assinaturaEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssinaturaEmpresa>>();
      const assinaturaEmpresa = { id: 123 };
      jest.spyOn(assinaturaEmpresaFormService, 'getAssinaturaEmpresa').mockReturnValue({ id: null });
      jest.spyOn(assinaturaEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assinaturaEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assinaturaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(assinaturaEmpresaFormService.getAssinaturaEmpresa).toHaveBeenCalled();
      expect(assinaturaEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssinaturaEmpresa>>();
      const assinaturaEmpresa = { id: 123 };
      jest.spyOn(assinaturaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assinaturaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assinaturaEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePeriodoPagamento', () => {
      it('Should forward to periodoPagamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(periodoPagamentoService, 'comparePeriodoPagamento');
        comp.comparePeriodoPagamento(entity, entity2);
        expect(periodoPagamentoService.comparePeriodoPagamento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareFormaDePagamento', () => {
      it('Should forward to formaDePagamentoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(formaDePagamentoService, 'compareFormaDePagamento');
        comp.compareFormaDePagamento(entity, entity2);
        expect(formaDePagamentoService.compareFormaDePagamento).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePlanoContabil', () => {
      it('Should forward to planoContabilService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(planoContabilService, 'comparePlanoContabil');
        comp.comparePlanoContabil(entity, entity2);
        expect(planoContabilService.comparePlanoContabil).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmpresa', () => {
      it('Should forward to empresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaService, 'compareEmpresa');
        comp.compareEmpresa(entity, entity2);
        expect(empresaService.compareEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
