import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { BancoDetailComponent } from './banco-detail.component';

describe('Banco Management Detail Component', () => {
  let comp: BancoDetailComponent;
  let fixture: ComponentFixture<BancoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BancoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: BancoDetailComponent,
              resolve: { banco: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BancoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BancoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load banco on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BancoDetailComponent);

      // THEN
      expect(instance.banco()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
