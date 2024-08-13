import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { SecaoCnaeService } from '../service/secao-cnae.service';
import { ISecaoCnae } from '../secao-cnae.model';
import { SecaoCnaeFormService } from './secao-cnae-form.service';

import { SecaoCnaeUpdateComponent } from './secao-cnae-update.component';

describe('SecaoCnae Management Update Component', () => {
  let comp: SecaoCnaeUpdateComponent;
  let fixture: ComponentFixture<SecaoCnaeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let secaoCnaeFormService: SecaoCnaeFormService;
  let secaoCnaeService: SecaoCnaeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SecaoCnaeUpdateComponent],
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
      .overrideTemplate(SecaoCnaeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SecaoCnaeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    secaoCnaeFormService = TestBed.inject(SecaoCnaeFormService);
    secaoCnaeService = TestBed.inject(SecaoCnaeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const secaoCnae: ISecaoCnae = { id: 456 };

      activatedRoute.data = of({ secaoCnae });
      comp.ngOnInit();

      expect(comp.secaoCnae).toEqual(secaoCnae);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISecaoCnae>>();
      const secaoCnae = { id: 123 };
      jest.spyOn(secaoCnaeFormService, 'getSecaoCnae').mockReturnValue(secaoCnae);
      jest.spyOn(secaoCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ secaoCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: secaoCnae }));
      saveSubject.complete();

      // THEN
      expect(secaoCnaeFormService.getSecaoCnae).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(secaoCnaeService.update).toHaveBeenCalledWith(expect.objectContaining(secaoCnae));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISecaoCnae>>();
      const secaoCnae = { id: 123 };
      jest.spyOn(secaoCnaeFormService, 'getSecaoCnae').mockReturnValue({ id: null });
      jest.spyOn(secaoCnaeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ secaoCnae: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: secaoCnae }));
      saveSubject.complete();

      // THEN
      expect(secaoCnaeFormService.getSecaoCnae).toHaveBeenCalled();
      expect(secaoCnaeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISecaoCnae>>();
      const secaoCnae = { id: 123 };
      jest.spyOn(secaoCnaeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ secaoCnae });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(secaoCnaeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
