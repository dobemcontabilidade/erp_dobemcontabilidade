import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ISecaoCnae } from 'app/entities/secao-cnae/secao-cnae.model';
import { SecaoCnaeService } from 'app/entities/secao-cnae/service/secao-cnae.service';
import { DivisaoCnaeService } from '../service/divisao-cnae.service';
import { IDivisaoCnae } from '../divisao-cnae.model';
import { DivisaoCnaeFormService } from './divisao-cnae-form.service';

import { DivisaoCnaeUpdateComponent } from './divisao-cnae-update.component';

describe('DivisaoCnae Management Update Component', () => {
  let comp: DivisaoCnaeUpdateComponent;
  let fixture: ComponentFixture<DivisaoCnaeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let divisaoCnaeFormService: DivisaoCnaeFormService;
  let divisaoCnaeService: DivisaoCnaeService;
  let secaoCnaeService: SecaoCnaeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DivisaoCnaeUpdateComponent],
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
      .overrideTemplate(DivisaoCnaeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DivisaoCnaeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    divisaoCnaeFormService = TestBed.inject(DivisaoCnaeFormService);
    divisaoCnaeService = TestBed.inject(DivisaoCnaeService);
    secaoCnaeService = TestBed.inject(SecaoCnaeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SecaoCnae query and add missing value', () => {
      const divisaoCnae: IDivisaoCnae = { id: 456 };
      const secao: ISecaoCnae = { id: 28729 };
      divisaoCnae.secao = secao;

      const secaoCnaeCollection: ISecaoCnae[] = [{ id: 754 }];
      jest.spyOn(secaoCnaeService, 'query').mockReturnValue(of(new HttpResponse({ body: secaoCnaeCollection })));
      const additionalSecaoCnaes = [secao];
      const expectedCollection: ISecaoCnae[] = [...additionalSecaoCnaes, ...secaoCnaeCollection];
      jest.spyOn(secaoCnaeService, 'addSecaoCnaeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ divisaoCnae });
      comp.ngOnInit();

      expect(secaoCnaeService.query).toHaveBeenCalled();
      expect(secaoCnaeService.addSecaoCnaeToCollectionIfMissing).toHaveBeenCalledWith(
        secaoCnaeCollection,
        ...additionalSecaoCnaes.map(expect.objectContaining),
      );
      expect(comp.secaoCnaesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const divisaoCnae: IDivisaoCnae = { id: 456 };
      const secao: ISecaoCnae = { id: 18799 };
      divisaoCnae.secao = secao;

      activatedRoute.data = of({ divisaoCnae });
      comp.ngOnInit();

      expect(comp.secaoCnaesSharedCollection).toContain(secao);
      expect(comp.divisaoCnae).toEqual(divisaoCnae);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDivisaoCnae>>();
      const divisaoCnae = { id: 123 };
      jest.spyOn(divisaoCnaeFormService, 'getDivisaoCnae').mockReturnValue(divisaoCnae);
      jest.spyOn(divisaoCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ divisaoCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: divisaoCnae }));
      saveSubject.complete();

      // THEN
      expect(divisaoCnaeFormService.getDivisaoCnae).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(divisaoCnaeService.update).toHaveBeenCalledWith(expect.objectContaining(divisaoCnae));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDivisaoCnae>>();
      const divisaoCnae = { id: 123 };
      jest.spyOn(divisaoCnaeFormService, 'getDivisaoCnae').mockReturnValue({ id: null });
      jest.spyOn(divisaoCnaeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ divisaoCnae: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: divisaoCnae }));
      saveSubject.complete();

      // THEN
      expect(divisaoCnaeFormService.getDivisaoCnae).toHaveBeenCalled();
      expect(divisaoCnaeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDivisaoCnae>>();
      const divisaoCnae = { id: 123 };
      jest.spyOn(divisaoCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ divisaoCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(divisaoCnaeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSecaoCnae', () => {
      it('Should forward to secaoCnaeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(secaoCnaeService, 'compareSecaoCnae');
        comp.compareSecaoCnae(entity, entity2);
        expect(secaoCnaeService.compareSecaoCnae).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
