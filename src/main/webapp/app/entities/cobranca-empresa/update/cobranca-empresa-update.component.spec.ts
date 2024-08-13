import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { AssinaturaEmpresaService } from 'app/entities/assinatura-empresa/service/assinatura-empresa.service';
import { IFormaDePagamento } from 'app/entities/forma-de-pagamento/forma-de-pagamento.model';
import { FormaDePagamentoService } from 'app/entities/forma-de-pagamento/service/forma-de-pagamento.service';
import { ICobrancaEmpresa } from '../cobranca-empresa.model';
import { CobrancaEmpresaService } from '../service/cobranca-empresa.service';
import { CobrancaEmpresaFormService } from './cobranca-empresa-form.service';

import { CobrancaEmpresaUpdateComponent } from './cobranca-empresa-update.component';

describe('CobrancaEmpresa Management Update Component', () => {
  let comp: CobrancaEmpresaUpdateComponent;
  let fixture: ComponentFixture<CobrancaEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cobrancaEmpresaFormService: CobrancaEmpresaFormService;
  let cobrancaEmpresaService: CobrancaEmpresaService;
  let assinaturaEmpresaService: AssinaturaEmpresaService;
  let formaDePagamentoService: FormaDePagamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CobrancaEmpresaUpdateComponent],
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
      .overrideTemplate(CobrancaEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CobrancaEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cobrancaEmpresaFormService = TestBed.inject(CobrancaEmpresaFormService);
    cobrancaEmpresaService = TestBed.inject(CobrancaEmpresaService);
    assinaturaEmpresaService = TestBed.inject(AssinaturaEmpresaService);
    formaDePagamentoService = TestBed.inject(FormaDePagamentoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AssinaturaEmpresa query and add missing value', () => {
      const cobrancaEmpresa: ICobrancaEmpresa = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 22923 };
      cobrancaEmpresa.assinaturaEmpresa = assinaturaEmpresa;

      const assinaturaEmpresaCollection: IAssinaturaEmpresa[] = [{ id: 22577 }];
      jest.spyOn(assinaturaEmpresaService, 'query').mockReturnValue(of(new HttpResponse({ body: assinaturaEmpresaCollection })));
      const additionalAssinaturaEmpresas = [assinaturaEmpresa];
      const expectedCollection: IAssinaturaEmpresa[] = [...additionalAssinaturaEmpresas, ...assinaturaEmpresaCollection];
      jest.spyOn(assinaturaEmpresaService, 'addAssinaturaEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cobrancaEmpresa });
      comp.ngOnInit();

      expect(assinaturaEmpresaService.query).toHaveBeenCalled();
      expect(assinaturaEmpresaService.addAssinaturaEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        assinaturaEmpresaCollection,
        ...additionalAssinaturaEmpresas.map(expect.objectContaining),
      );
      expect(comp.assinaturaEmpresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call FormaDePagamento query and add missing value', () => {
      const cobrancaEmpresa: ICobrancaEmpresa = { id: 456 };
      const formaDePagamento: IFormaDePagamento = { id: 16126 };
      cobrancaEmpresa.formaDePagamento = formaDePagamento;

      const formaDePagamentoCollection: IFormaDePagamento[] = [{ id: 2123 }];
      jest.spyOn(formaDePagamentoService, 'query').mockReturnValue(of(new HttpResponse({ body: formaDePagamentoCollection })));
      const additionalFormaDePagamentos = [formaDePagamento];
      const expectedCollection: IFormaDePagamento[] = [...additionalFormaDePagamentos, ...formaDePagamentoCollection];
      jest.spyOn(formaDePagamentoService, 'addFormaDePagamentoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cobrancaEmpresa });
      comp.ngOnInit();

      expect(formaDePagamentoService.query).toHaveBeenCalled();
      expect(formaDePagamentoService.addFormaDePagamentoToCollectionIfMissing).toHaveBeenCalledWith(
        formaDePagamentoCollection,
        ...additionalFormaDePagamentos.map(expect.objectContaining),
      );
      expect(comp.formaDePagamentosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cobrancaEmpresa: ICobrancaEmpresa = { id: 456 };
      const assinaturaEmpresa: IAssinaturaEmpresa = { id: 13185 };
      cobrancaEmpresa.assinaturaEmpresa = assinaturaEmpresa;
      const formaDePagamento: IFormaDePagamento = { id: 23787 };
      cobrancaEmpresa.formaDePagamento = formaDePagamento;

      activatedRoute.data = of({ cobrancaEmpresa });
      comp.ngOnInit();

      expect(comp.assinaturaEmpresasSharedCollection).toContain(assinaturaEmpresa);
      expect(comp.formaDePagamentosSharedCollection).toContain(formaDePagamento);
      expect(comp.cobrancaEmpresa).toEqual(cobrancaEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICobrancaEmpresa>>();
      const cobrancaEmpresa = { id: 123 };
      jest.spyOn(cobrancaEmpresaFormService, 'getCobrancaEmpresa').mockReturnValue(cobrancaEmpresa);
      jest.spyOn(cobrancaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cobrancaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cobrancaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(cobrancaEmpresaFormService.getCobrancaEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cobrancaEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(cobrancaEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICobrancaEmpresa>>();
      const cobrancaEmpresa = { id: 123 };
      jest.spyOn(cobrancaEmpresaFormService, 'getCobrancaEmpresa').mockReturnValue({ id: null });
      jest.spyOn(cobrancaEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cobrancaEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cobrancaEmpresa }));
      saveSubject.complete();

      // THEN
      expect(cobrancaEmpresaFormService.getCobrancaEmpresa).toHaveBeenCalled();
      expect(cobrancaEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICobrancaEmpresa>>();
      const cobrancaEmpresa = { id: 123 };
      jest.spyOn(cobrancaEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cobrancaEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cobrancaEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAssinaturaEmpresa', () => {
      it('Should forward to assinaturaEmpresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(assinaturaEmpresaService, 'compareAssinaturaEmpresa');
        comp.compareAssinaturaEmpresa(entity, entity2);
        expect(assinaturaEmpresaService.compareAssinaturaEmpresa).toHaveBeenCalledWith(entity, entity2);
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
  });
});
